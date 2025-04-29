document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".menu-item img").forEach(img => {
        img.addEventListener("click", function () {
            let details = this.nextElementSibling;

            if (details.style.maxHeight) {
                details.style.maxHeight = null;
            } else {
                document.querySelectorAll(".menu-details").forEach(el => el.style.maxHeight = null);
                details.style.maxHeight = details.scrollHeight + "px";
            }
        });
    });
});

function changeQuantity(change, itemId) {
    const input = document.getElementById('quantity-' + itemId);
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

document.querySelectorAll('.status-select').forEach(select => {
    select.addEventListener('change', () => {
        select.form.submit();
    });
});

function initStatusSelects() {
    document.querySelectorAll('.status-select').forEach(select => {
        select.addEventListener('change', () => {
            select.form.submit();
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    const textareas = document.querySelectorAll('.textarea-field');

    textareas.forEach(textarea => {
        const adjustHeight = () => {
            textarea.style.height = 'auto';
            textarea.style.height = `${textarea.scrollHeight}px`;
        };

        adjustHeight();
        textarea.addEventListener('input', adjustHeight);
        textarea.addEventListener('change', adjustHeight);
    });
});


document.addEventListener('DOMContentLoaded', () => {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.display = 'none';
        }, 5000);
    });
});
