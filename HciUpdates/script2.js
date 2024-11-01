document.addEventListener('DOMContentLoaded', () => {
    const editBtn = document.getElementById('edit-btn');
    const editPanel = document.getElementById('edit-panel');
    const modal = document.getElementById('modal');
    const modalTitle = document.getElementById('modal-title');
    const modalInput = document.getElementById('modal-input');
    const modalConfirm = document.getElementById('modal-confirm');
    const modalCancel = document.getElementById('modal-cancel');
    const addBlockBtn = document.getElementById('add-block');
    const changeColorBtn = document.getElementById('change-color');
    const changeBorderColorBtn = document.getElementById('change-border-color');
    const columns = document.querySelectorAll('.column');
    const contextMenu = document.getElementById('context-menu');
    const subjectDropdown = document.getElementById('subject-dropdown');
    const renameBtn = document.getElementById('rename-btn'); // New rename button
    let selectedBlock = null;

    // Toggle edit panel visibility
    editBtn.addEventListener('click', () => {
        editPanel.style.display = editPanel.style.display === 'none' ? 'block' : 'none';
    });

    // Attach draggable events to blocks
    const attachDraggableEvents = (draggable) => {
        draggable.addEventListener('dragstart', (event) => {
            event.dataTransfer.setData('text/plain', event.target.id);
            event.target.classList.add('dragging');
        });

        draggable.addEventListener('dragend', (event) => {
            event.target.classList.remove('dragging');
        });

        // Click to select the block and darken it
        draggable.addEventListener('click', () => {
            // Deselect previously selected block
            if (selectedBlock) {
                selectedBlock.classList.remove('selected');
            }
            // Select new block and apply darkening
            selectedBlock = draggable;
            selectedBlock.classList.add('selected');
        });

        // Right-click to open context menu
        draggable.addEventListener('contextmenu', (e) => {
            e.preventDefault();
            selectedBlock = draggable;
            contextMenu.style.top = `${e.pageY}px`;
            contextMenu.style.left = `${e.pageX}px`;
            contextMenu.style.display = 'block';

            // Show or hide the "Insert Subject" option based on the selected block
            const insertSubjectBtn = document.getElementById('insert-subject-btn');
            if (selectedBlock.classList.contains('free-elective')) {
                insertSubjectBtn.style.display = 'block'; // Show if it's a free elective
            } else {
                insertSubjectBtn.style.display = 'none'; // Hide otherwise
            }
        });
    };

    // Add event listeners to columns for drop functionality
    columns.forEach((column) => {
        column.addEventListener('dragover', (event) => {
            event.preventDefault();
        });

        column.addEventListener('drop', (event) => {
            event.preventDefault();
            const id = event.dataTransfer.getData('text/plain');
            const draggable = document.getElementById(id);
            if (draggable) {
                column.appendChild(draggable);
            }
        });
    });

    // Add block to the first column
    addBlockBtn.addEventListener('click', () => {
        const newBlock = document.createElement('div');
        newBlock.classList.add('draggable', 'free-elective'); // Add free-elective class
        newBlock.textContent = 'New Block';
        newBlock.id = `item${document.querySelectorAll('.draggable').length + 1}`;
        newBlock.setAttribute('draggable', 'true');
        attachDraggableEvents(newBlock);
        columns[0].appendChild(newBlock);
    });

    // Change block color
    changeColorBtn.addEventListener('click', () => {
        if (!selectedBlock) {
            alert('Select a block first!');
            return;
        }
        const color = prompt('Enter a color (name or hex code):');
        if (color) {
            selectedBlock.style.backgroundColor = color; // Change color
        }
    });

    // Change block border color
    changeBorderColorBtn.addEventListener('click', () => {
        if (!selectedBlock) {
            alert('Select a block first!');
            return;
        }
        const borderColor = prompt('Enter a border color (name or hex code):');
        if (borderColor) {
            selectedBlock.style.borderColor = borderColor; // Change border color
        }
    });

    // Insert subject from context menu
    document.getElementById('insert-subject-btn').addEventListener('click', () => {
        if (!selectedBlock || !selectedBlock.classList.contains('free-elective')) {
            alert('Select a Free Elective block first!');
            return;
        }

        subjectDropdown.style.display = 'block';
        subjectDropdown.value = ''; // Reset the dropdown

        // Show the dropdown next to the selected block
        const rect = selectedBlock.getBoundingClientRect();
        subjectDropdown.style.position = 'absolute';
        subjectDropdown.style.top = `${rect.bottom + window.scrollY}px`;
        subjectDropdown.style.left = `${rect.left + window.scrollX}px`;

        // Handle subject selection
        subjectDropdown.onchange = function() {
            const selectedSubject = subjectDropdown.value;
            if (selectedSubject) {
                selectedBlock.textContent = selectedSubject; // Replace text with selected subject
                selectedBlock.classList.remove('free-elective'); // Remove free elective class
                subjectDropdown.style.display = 'none'; // Hide dropdown after selection
            }
        };
    });

    // Rename the selected block
    renameBtn.addEventListener('click', () => {
        if (!selectedBlock) {
            alert('Select a block first!');
            return;
        }
        // Clear the existing text
        selectedBlock.textContent = '';
        selectedBlock.contentEditable = 'true'; // Make it editable
        selectedBlock.focus(); // Focus the block for immediate editing

        // Handle blur event to make it non-editable
        selectedBlock.addEventListener('blur', () => {
            selectedBlock.contentEditable = 'false'; // Make it non-editable
            selectedBlock.classList.remove('selected'); // Deselect the block
        });
    });


    // Remove the selected block
    document.getElementById('remove-btn').addEventListener('click', () => {
        if (!selectedBlock) {
            alert('Select a block first!');
            return;
        }
        selectedBlock.remove(); // Remove the block from the DOM
        contextMenu.style.display = 'none'; // Hide the context menu
        selectedBlock = null; // Reset the selected block
    });

    // Initialize context menu
    document.addEventListener('click', (e) => {
        if (e.target !== contextMenu) {
            contextMenu.style.display = 'none';
        }
    });

    // Initialize draggable blocks
    document.querySelectorAll('.draggable').forEach(attachDraggableEvents);
});
