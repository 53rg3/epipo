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
        success: function(data) {
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


$("#save_config_button").click(putConfig)
getConfig()
