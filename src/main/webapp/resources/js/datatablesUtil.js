function makeEditable() {
    $('#add').click(function () {
        //   $('#id').val(0);
        //    $('#dateTime').val(dt.toISOString().slice(0, 19) + 'Z');
        //    $('#dateTime').val("2016-07-26T07:41");
        //      var dateTime = $('#dateTime').val();
        $('#dateTime').val(new Date().toISOString().slice(0, 16));
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).parents('tr').attr("id"));
    });
    /*
     $('.enabled').click(function () {
     //    var form = $('#detailsForm');
     debugger;
     /!*
     var id = $(this).parents('tr').attr("id");
     var name = $(this).parents('tr').find('#nameRow').text();
     var password = $(this).parents('tr').attr("password");
     var email = $(this).parents('tr').children('#emailRow').text();
     var enabled = $(this).is(':checked')
     *!/

     /!*
     var data = dataTableApi.row($(this).closest('tr')).data();
     *!/

     //  var password = $(this).parents('tr').children('#passwordRow').text();
     /!*        form.find('#id').val($(this).parents('tr').attr("id"));
     form.find('#name').val($(this).parents('tr').children('#nameRow').text())
     // form.find('#id').val(123);
     //    form.find('#name').val("sdfaadf")
     form.find('#email').val($(this).parents('tr').children('#emailRow').text())
     form.find('#password').val($(this).parents('tr').children('#passwordRow').text())
     form.find('#enabled').val($(this).parents('tr').children('#enabled')())
     *!/       //      var id = $(this).parents('tr').attr("id");

     save(data.id, data.name, data.email, data.password, data.enabled);
     });
     */
}

function enable(id, name, email, password, enabled) {
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: {"id":id,
            "name" : name,
            "email" : email,
            "password" : password,
            "enabled" : !enabled},
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty(enabled === true ? 'User Enabled' : 'User Disabled');
        }
    });

    /*
     $('.edit').click(function () {
     $('#id').val($(this).attr("id"));
     //  $('#description').val("my new description");
     $('#description').val($('#datatable').find('#description').value);
     //   $('#calories').val($('#editRow').find('calories').value);
     $('#editRow').modal();
     });
     */

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}


function enableUser(id, enabled) {
    $.ajax({
        type: "PUT",
        url: ajaxUrl + id,
        data: "" +  qenabled,
        success: function () {
            updateTable();
            successNoty(enabled === true ? 'User Enabled' : 'User Diabled');
        }
    });
}


function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.fnClearTable();
        $.each(data, function (key, item) {
            datatableApi.fnAddData(item);
        });
        datatableApi.fnDraw();
    });
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
