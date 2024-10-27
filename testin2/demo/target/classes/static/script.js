document.addEventListener('DOMContentLoaded', () => {
    const editBtn = document.getElementById('edit-btn');
    const editPanel = document.getElementById('edit-panel');
    const modal = document.getElementById('modal');
    const modalTitle = document.getElementById('modal-title');
    const modalInput = document.getElementById('modal-input');
    const modalConfirm = document.getElementById('modal-confirm');
    const modalCancel = document.getElementById('modal-cancel');
    const addBlockBtn = document.getElementById('add-block');
    const renameBlockBtn = document.getElementById('rename-block');
    const changeColorBtn = document.getElementById('change-color');
    const changeBorderColorBtn = document.getElementById('change-border-color');
    const columns = document.querySelectorAll('.column');
    let selectedBlock = null;
    let currentAction = null;

    // Toggle edit panel visibility
    editBtn.addEventListener('click', () => {
        editPanel.style.display = editPanel.style.display === 'none' ? 'block' : 'none';
    });

    // Show modal for different actions
    const showModal = (action, title) => {
        currentAction = action;
        modalTitle.textContent = title;
        modalInput.value = '';
        modal.style.display = 'block';
    };

    // Close the modal
    const closeModal = () => {
        modal.style.display = 'none';
    };

    // Attach draggable events to new or existing blocks
    const attachDraggableEvents = (draggable) => {
        draggable.addEventListener('dragstart', (event) => {
            if (selectedBlock === draggable) {
                event.dataTransfer.setData('text/plain', event.target.id);
                event.target.classList.add('dragging');
                event.target.style.boxShadow = '5px 5px 15px rgba(0, 0, 0, 0.3)';
            } else {
                event.preventDefault();
            }
        });

        draggable.addEventListener('dragend', (event) => {
            event.target.classList.remove('dragging');
            event.target.style.boxShadow = 'none';
        });

        draggable.addEventListener('click', () => {
            if (selectedBlock) {
                selectedBlock.classList.remove('selected');
            }
            selectedBlock = draggable;
            selectedBlock.classList.add('selected');
        });
    };

    // Add event listeners to columns for drop functionality
    columns.forEach((column) => {
        column.addEventListener('dragover', (event) => {
            event.preventDefault();
        });

        column.addEventListener('drop', (event) => {
            const id = event.dataTransfer.getData('text/plain');
            const draggable = document.getElementById(id);
            if (selectedBlock === draggable) {
                column.appendChild(draggable);
            }
        });
    });

    // Add block to the first column
    addBlockBtn.addEventListener('click', () => {
        const newBlock = document.createElement('div');
        newBlock.classList.add('draggable');
        newBlock.textContent = 'New Block';
        newBlock.id = `item${document.querySelectorAll('.draggable').length + 1}`;
        newBlock.setAttribute('draggable', 'true');
        attachDraggableEvents(newBlock);
        columns[0].appendChild(newBlock);
    });

    // Rename the selected block
    renameBlockBtn.addEventListener('click', () => {
        if (!selectedBlock) {
            alert('Select a block first!');
            return;
        }
        showModal('rename', 'Rename Block');
    });

    // Change block color
    changeColorBtn.addEventListener('click', () => {
        if (!selectedBlock) {
            alert('Select a block first!');
            return;
        }
        showModal('color', 'Change Block Color');
    });

    // Change block border color
    changeBorderColorBtn.addEventListener('click', () => {
        if (!selectedBlock) {
            alert('Select a block first!');
            return;
        }
        showModal('borderColor', 'Change Border Color');
    });

    // Confirm modal actions
    modalConfirm.addEventListener('click', () => {
        const value = modalInput.value.trim();
        if (currentAction === 'rename' && value) {
            selectedBlock.textContent = value;
        } else if (currentAction === 'color' && value) {
            selectedBlock.style.backgroundColor = value;
        } else if (currentAction === 'borderColor' && value) {
            selectedBlock.style.borderColor = value;
        }
        closeModal();
    });

    // Cancel modal
    modalCancel.addEventListener('click', closeModal);

    // Initialize all existing draggable blocks
    document.querySelectorAll('.draggable').forEach(attachDraggableEvents);
});
