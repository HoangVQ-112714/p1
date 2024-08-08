$(document).ready(function () {
    $('.delete-category').on('click', function (event) {
        let id = $(this).data('delete_id')
        console.log(id)
        $('#modal-delete-category').find('.delete').attr('id', id)
    })

    $('#modal-delete-category .delete').on('click', function (event) {
        let id = $(this).attr('id');
        let formData = new FormData();
        formData.append('id', id);

        $.ajax({
            url: '/admin/categories/delete/' + id,
            type: 'GET',
            success: function () {
                return window.location.reload();
            },
        })
    })
});