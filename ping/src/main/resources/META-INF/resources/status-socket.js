let statusConnected = false;
let statusSocket;

let statusConnect = function () {
    if (!statusConnected) {
        statusSocket = new WebSocket("ws://" + location.host + "/status");
        statusSocket.onopen = function () {
            statusConnected = true;
            console.log("Connected to the status websocket");
        };
        statusSocket.onmessage = function (m) {
            let response = JSON.parse(m.data)
            $("#pong-count").text(response.pongCount);

            let checksHtml = "<li>Ping Service: " + response.health.status + "</li>";
            response.health.checks.forEach((check) => {
                checksHtml += "<li>" + check.name + ": " + check.status + "</li>";
            })
            $("#status-list").html(checksHtml);
        };
    }
};
statusConnect();
$("#pong-count").text("0");
