$(document).ready(function () {
    // Add click event listener to all images in media container
    $('#media-container img').on('click', function () {
        // Get source of clicked image
        const src = $(this).attr('src');
        // Create and show a modal with the clicked image
        const modal = $('<div class="modal"></div>');
        const modalImg = $('<img>').attr('src', src);
        const closeModal = $('<span class="close">&times;</span>');
        modal.append(modalImg, closeModal);
        $('body').append(modal);
        // Add click event listener to close modal
        closeModal.on('click', function () {
            modal.remove();
        });
    });
});
