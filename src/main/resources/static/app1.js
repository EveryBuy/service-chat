const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chat/15', (messageResponse) => {
        showGreeting(JSON.parse(messageResponse.body));
    });
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
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/chat/15",
        body: JSON.stringify({
            'text': $("#text").val()
        })
    });
}

// function showGreeting(messageResponse) {
//     $("#messageResponse").append("<tr><td>" + messageResponse + "</td></tr>");
// }

// function showGreeting(message) {
//     $("#messageResponse").append("<tr><td>" + (message.text || 'No message') + "</td></tr>");
// }

function showGreeting(messageResponse) {
    const row = `<tr>
                    <td>${messageResponse.id}</td>
                    <td>${messageResponse.text}</td>
                    <td>${messageResponse.creationTime}</td>
                    <td>${messageResponse.userId}</td>
                    <td>${messageResponse.chatId}</td>
                    <td>${messageResponse.fileUrl}</td>
                </tr>`;
    $("#messageResponse").append(row);
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});