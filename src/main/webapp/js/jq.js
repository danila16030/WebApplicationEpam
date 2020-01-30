$(document).on('click', '#btn', function () {
    var formData = new FormData();
    formData.append("myFile", document.getElementById("file").files[0], 'chris1.jpg');

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "index.php");
    xhr.send(formData);
});