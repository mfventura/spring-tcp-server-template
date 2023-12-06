var globalName = '';

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', (message) => {
        showMessage(JSON.parse(message.body).name, JSON.parse(message.body).content);
    }, {'user': $("#name").val()});
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    if($("#name").val() === '') {
        alert('Please enter your name before connecting');
        return;
    }
    globalName = $("#name").val();
    $("#name").prop("disabled", true);

    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    $("#name").prop("disabled", false);
    globalName = '';
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/api/message",
        body: JSON.stringify({'name': $("#name").val(), 'content': $("#content").val()})
    });
    $('#content').val('');
}

function showMessage(name, message) {
    globalName === name ? $("#messages").append("<tr><td style='text-align: right'>" + name + ": " + message + "</td></tr>") :
    $("#messages").append("<tr><td>" + name + ": " + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});