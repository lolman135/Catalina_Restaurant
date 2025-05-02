document.addEventListener('DOMContentLoaded', () => {
    // Debounce utility
    const debounce = (func, wait) => {
        let timeout;
        return (...args) => {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), wait);
        };
    };

    document.querySelectorAll('.menu-item img').forEach(img => {
        img.addEventListener('click', () => {
            const details = img.nextElementSibling;
            if (details.style.maxHeight) {
                details.style.maxHeight = null;
            } else {
                document.querySelectorAll('.menu-details').forEach(el => el.style.maxHeight = null);
                details.style.maxHeight = `${details.scrollHeight}px`;
            }
        });
    });

    document.querySelectorAll('.status-select').forEach(select => {
        const form = select.form;
        const submitForm = debounce(() => {
            if (select.checkValidity()) {
                form.submit();
            }
        }, 500);
        select.addEventListener('change', submitForm);
    });

    document.querySelectorAll('.textarea-field').forEach(textarea => {
        const adjustHeight = () => {
            textarea.style.height = 'auto';
            textarea.style.height = `${textarea.scrollHeight}px`;
        };
        adjustHeight();
        textarea.addEventListener('input', adjustHeight);
        textarea.addEventListener('change', adjustHeight);
    });

    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', (e) => {
            if (!confirm('Are you sure you want to delete this menu item?')) {
                e.preventDefault();
            }
        });
    });

    document.querySelectorAll('.alert').forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                alert.style.display = 'none';
            }, 500);
        }, 3000);
    });

    const addForm = document.querySelector('form[action$="/add"]');
    if (addForm) {
        addForm.addEventListener('submit', (e) => {
            if (!confirm('Add this menu item?')) {
                e.preventDefault();
            }
        });
    }

    const dragDropZone = document.getElementById('dragDropZone');
    const fileInput = document.getElementById('image');
    const fileNameDisplay = document.getElementById('fileName');
    const previewContainer = document.getElementById('previewImageContainer');

    if (dragDropZone && fileInput && fileNameDisplay && previewContainer) {
        // Prevent default drag behaviors
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            dragDropZone.addEventListener(eventName, preventDefaults, false);
        });

        function preventDefaults(e) {
            e.preventDefault();
            e.stopPropagation();
        }

        // Highlight drop zone on drag
        ['dragenter', 'dragover'].forEach(eventName => {
            dragDropZone.addEventListener(eventName, () => {
                dragDropZone.classList.add('dragover');
                console.log('Drag event:', eventName);
            }, false);
        });

        ['dragleave', 'drop'].forEach(eventName => {
            dragDropZone.addEventListener(eventName, () => {
                dragDropZone.classList.remove('dragover');
                console.log('Drag event:', eventName);
            }, false);
        });

        // Handle dropped files
        dragDropZone.addEventListener('drop', (e) => {
            const files = e.dataTransfer.files;
            console.log('Files dropped:', files.length > 0 ? files[0].name : 'None');
            if (files.length > 0) {
                fileInput.files = files;
                updateFileName(files[0]);
                previewImage(files[0]);
            }
        }, false);

        // Handle file selection via input
        fileInput.addEventListener('change', () => {
            if (fileInput.files.length > 0) {
                console.log('File selected via input:', fileInput.files[0].name);
                updateFileName(fileInput.files[0]);
                previewImage(fileInput.files[0]);
            } else {
                console.log('No file selected');
                fileNameDisplay.textContent = '';
                previewContainer.innerHTML = '';
            }
        });

        // Update file name display
        function updateFileName(file) {
            fileNameDisplay.textContent = `Selected: ${file.name}`;
        }

        // Preview image
        function previewImage(file) {
            if (file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    console.log('File preview loaded');
                    previewContainer.innerHTML = '';
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.style.maxWidth = '200px';
                    img.style.borderRadius = '8px';
                    img.style.marginTop = '10px';
                    previewContainer.appendChild(img);
                };
                reader.onerror = (error) => {
                    console.error('Error reading file:', error);
                };
                reader.readAsDataURL(file);
            } else {
                console.log('Non-image file selected');
                previewContainer.innerHTML = '';
            }
        }
    } else {
        console.error('File input elements not found:', {
            dragDropZone: !!dragDropZone,
            fileInput: !!fileInput,
            fileNameDisplay: !!fileNameDisplay,
            previewContainer: !!previewContainer
        });
    }
});

function changeQuantity(change, itemId) {
    const input = document.getElementById(`quantity-${itemId}`);
    let value = parseInt(input.value) + change;
    if (value < 1) value = 1;
    input.value = value;
}

function showPopup(event, form) {
    event.preventDefault();

    const itemId = form.querySelector('input[name="itemId"]').value;
    const quantity = form.querySelector('.quantity-input').value;
    const menuItem = document.getElementById(itemId);
    const itemName = menuItem.querySelector('h2').textContent;
    const itemPrice = menuItem.querySelector('.price').textContent;

    document.getElementById('popupItemName').textContent = itemName;
    document.getElementById('popupQuantity').textContent = `Quantity: ${quantity}`;
    document.getElementById('popupPrice').textContent = itemPrice;

    document.getElementById('cartPopup').style.display = 'block';

    fetch(form.action, {
        method: 'POST',
        body: new FormData(form)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });

    return false;
}

function closePopup() {
    document.getElementById('cartPopup').style.display = 'none';
}