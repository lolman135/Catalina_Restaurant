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
        }, 10000);
    });

    const addForm = document.querySelector('form[action$="/add"]');
    if (addForm) {
        addForm.addEventListener('submit', (e) => {
            if (!confirm('Add this menu item?')) {
                e.preventDefault();
            }
        });
    }

    const fileInput = document.querySelector('input[type="file"]#image');
    const fileLabel = document.querySelector('.file-input-label');
    const fileNameDisplay = document.querySelector('.file-name');
    const previewContainer = document.querySelector('.preview-image-container');

    if (fileInput && fileLabel && fileNameDisplay && previewContainer) {
        fileInput.addEventListener('change', (e) => {
            const file = e.target.files[0];
            console.log('File selected:', file ? file.name : 'None');
            if (file) {
                fileLabel.textContent = file.name;
                fileNameDisplay.textContent = `Selected: ${file.name}`;

                const reader = new FileReader();
                reader.onload = (event) => {
                    console.log('File preview loaded');
                    previewContainer.innerHTML = '';
                    const img = document.createElement('img');
                    img.src = event.target.result;
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
                fileLabel.textContent = 'No file chosen';
                fileNameDisplay.textContent = '';
                previewContainer.innerHTML = '';
            }
        });
    } else {
        console.error('File input elements not found:', {
            fileInput: !!fileInput,
            fileLabel: !!fileLabel,
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