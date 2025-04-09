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