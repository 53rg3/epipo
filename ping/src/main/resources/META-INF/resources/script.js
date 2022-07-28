// (function poll() {
//     $.ajax({
//         url: "/config", # todo metrics endpoint
//         type: "GET",
//         success: function(data) {
//             console.log("polling"); # todo replace HTML here
//         },
//         dataType: "json",
//         complete: setTimeout(function() {poll()}, 1000),
//         timeout: 1000
//     })
// })();

function getConfig() {
    $.ajax({
        url: "/config",
        type: "GET",
        success: function (data) {
            $("#config_field").val(JSON.stringify(data, null, 2))
        },
        dataType: "json",
        error: (e) => console.log("Failed to get config", e)
    })
}

function putConfig() {
    $.ajax({
        url: "/config",
        type: 'PUT',
        data: $("#config_field").val(),
        headers: {
            "Content-Type": "application/json"
        },
        success: () => {
            getConfig()
            showToastSuccess("Success", "Updated config");
        },
        error: (e) => {
            console.log("Failed to update config", e)
            showToastError("Error", "Failed to update config, check JS console")
        },
        dataType: 'text'
    })
}

function sendPings() {
    $.ajax({
        url: "/send-pings",
        type: 'POST',
        data: "",
        headers: {
            "Content-Type": "application/json"
        },
        success: () => {
            showToastSuccess("Success", "Pings sent");
        },
        error: (jqXHR) => {
            console.log("Failed to update config", jqXHR.responseText)
            showToastError("Error", jqXHR.responseText)
        },
        dataType: 'text'
    })
}

function showToastError(heading, text) {
    $.toast({
        heading: heading,
        text: text,
        showHideTransition: 'fade',
        icon: 'error'
    })
}

function showToastSuccess(heading, text) {
    $.toast({
        heading: heading,
        text: text,
        showHideTransition: 'fade',
        icon: 'success'
    })
}

$(document).ready(function () {
    $("#save_config_button").click(putConfig)
    $("#send-pings").click(sendPings)
    getConfig()
})
