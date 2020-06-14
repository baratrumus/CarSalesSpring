

function toMain() {
    window.location.href = '/';
}


function getModelsById(brand_id) {
    var selectedValue;
    if (brand_id === '0') {
        selectedValue = $('select[name="brands"]').val();
    } else {
        selectedValue = brand_id;
    }

    $.ajax('getModels', {
        method : 'get',
        data: {
            brandId: selectedValue
        },
        complete: function(data) {
            if (typeof data === "undefined") {
                return;
            }
            console.log(data.responseJSON);
            var modelsList = data.responseJSON;
            console.log(modelsList);
            var selectModels = $('#models');
            selectModels.find("option").remove();
            $.each(modelsList, function (key, value) {
                var id = value.id;
                var name = value.modelName;
                $("<option>").val(id).text(name).appendTo(selectModels);
                // selectModels.append('<option value=' + id + '>' + name + '</option>');
            });
        }
    });
}

function validateNewAd() {
    var ret = false;

    var ml = validateMileage();
    var cy = validateYear();
    var cl = validateColor();
    var pr = validatePrice();

    if (ml && cy && cl && pr) {
        ret = true;
    }
    return ret;
}

function validateColor() {
    var color = $('#color').val();
    if (color.trim() === '')  {
        printError('colorError', 'color','Fill the color.');
        return false;
    } else {
        $('#colorError').empty();
        $('#color').css("border", '2px solid lightgreen');
        return true;
    }
}

function validateYear() {
    var caryear = $('#caryear').val();
    if (caryear.trim() === '')  {
        printError('yearError', 'caryear','Fill the car year.');
        return false;
    } else {
        $('#yearError').empty();
        $('#caryear').css("border", '2px solid lightgreen');
        return true;
    }
}

function validatePrice() {
    var price = $('#price').val();

    if (price.trim() === '')  {
        printError('priceError', 'price','Fill the price.');
        return false;
    } else if (!isFinite(price)) {
        printError('priceError', 'price','Input digits!');
        return false;
    } else {
        $('#priceError').empty();
        $('#price').css("border", '2px solid lightgreen');
        return true;
    }
}

function validateMileage() {
    var mileage = $('#mileage').val();

    if (mileage.trim() === '')  {
        printError('mileageError', 'mileage','Fill the mileage.');
        return false;
    } else if (!isFinite(mileage)) {
        printError('mileageError', 'mileage','Input digits!');
        return false;
    } else {
        $('#mileageError').empty();
        $('#mileage').css("border", '2px solid lightgreen');
        return true;
    }
}


function showTestRoles() {
    $('#topBar #topBarIn #rolesButtons').empty();
    $('#topBar #topBarIn #rolesButtons').append("<div class='boxRoles'><form action='/auth' method='post'>" +
        "<button type='submit' class='buttonRoles'>User</button>" +
        "<input type=\"hidden\" name='login' value='user'></form>" +

        "<form action='/auth' method='post'>" +
        "<button type='submit' class='buttonRoles'>Admin</button>" +
        "<input type=\"hidden\" name='login' id=\"login\" value='admin'></form></div>"
        );
}

function printError(errorElem, inputElem, message) {
    $('#' + errorElem).empty();
    $('#' + errorElem).append("<label style='color: darksalmon; font-size: 16px;'>" + message + "</label>");
    $('#' + inputElem).css({
        "border-width" : "2px",
        "border-color" : "darksalmon",
        "border-style" : "solid",
    });
}





