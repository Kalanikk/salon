document.querySelectorAll('.rating').forEach(ratingDiv => {
    const stars = ratingDiv.querySelectorAll('.fa-star');
    stars.forEach((star, index) => {
        star.addEventListener('click', () => {

            stars.forEach((s, i) => {
                if (i <= index) {
                    s.classList.add('checked');
                } else {
                    s.classList.remove('checked');
                }
            });
            alert(`You rated this ${index + 1} stars!`);
        });
    });
});
