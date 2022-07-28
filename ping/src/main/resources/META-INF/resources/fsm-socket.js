let fsmConnected = false;
let fsmSocket;

let fsmConnect = function () {
    if (!fsmConnected) {
        fsmSocket = new WebSocket("ws://" + location.host + "/fsm");
        fsmSocket.onopen = function () {
            fsmConnected = true;
            console.log("Connected to the FSM websocket");
        };
        fsmSocket.onmessage = function (m) {
            if (m.data === "AVAILABLE") {
                showToastSuccess("ServiceState is " + m.data);
            }
            if (m.data === "UNAVAILABLE") {
                showToastError("ServiceState is " + m.data);
            }
        };
    }
};
fsmConnect();
