var stompClient = null ;
var username = null ;
var colors = ['#D2691E' , '#DC143C' , '#7FFF00' ,'#00FFFF' , '#8A2BE2' , '#1ABC9C'] ;
var userColor = '' ;
function getSenderColor() {
    return colors[getRandomIntInclusive( 0 , colors.length)];
}

function getRandomIntInclusive(min, max) {
    min = Math.ceil(min);   // Ensure min is an integer
    max = Math.floor(max); // Ensure max is an integer
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function connect(event) {

    console.log('2') ;
    username = document.getElementById("SenderName").value ;
    userColor = getSenderColor() ;
    console.log('username : ' +  username) ;
    if(username === '') {
        alert("UserName la emana rayi ra pumka!!! ") ;
        return false ;
    }
    var socket = new SockJS('/ws') ;
    stompClient = Stomp.over(socket) ;

    stompClient.connect({} , onConnected , onError) ;

    event.preventDefault() ;
}

function onConnected() {
    console.log('3') ;

    // Fix The UserName From Altering
    var senderNameInput = document.getElementById('SenderName') ;
    senderNameInput.disabled = true;

    // Disspaear the Fix-Name Button for furthur clicking it
    var disspearBtn = document.getElementById('nameBtn') ;
    disspearBtn.disabled = true;
    setTimeout(() => {
                    disspearBtn.style.display = 'none'; // Change display to 'none' to remove it from layout
                    console.log('Action button completely vanished from the DOM.');
                }, 500);

    // subscribe to the public/topic
    stompClient.subscribe('/topic/public' , onMessageReceived) ;

    // tell username to server
    stompClient.send('/app/chat.addUser' , {} , JSON.stringify({sender : username , messageType : 'JOIN' , senderColor : userColor})) ;
}

function onMessageReceived(payload) {
    console.log('4') ;
    var message = JSON.parse(payload.body) ;

    var chat = document.getElementById('MessagesUL') ;
    var singleMessageBox = document.createElement('li') ; 
    var messageElement = document.createElement('h3') ;

    console.log("username : " + message.sender) ;
    console.log("senderColor : " + message.senderColor) ;
    console.log("Message.Type : " + message.messageType) ;


    if(message.messageType === 'JOIN') {
        console.log('5-1') ;
        messageElement.textContent = message.sender + ' : ' + "Joined The Chat" ;
        messageElement.style.color = 'green'  ;
    }
    else if(message.messageType === 'LEFT') {
        console.log('5-2') ;
        messageElement.textContent = message.sender + ' : ' + "Left The Chat.." ;
        messageElement.style.color = 'red' ;
    }
    else {
        console.log('5-3') ;
        messageElement.textContent = message.sender + ' : ' + message.content ;
        messageElement.style.color = message.senderColor  ;
    }
    // styling the messageElement
    console.log("user-color : " + message.senderColor) ;
    singleMessageBox.appendChild(messageElement);
    chat.appendChild(singleMessageBox);
    chat.scrollTop = chat.scrollHeight ;

    console.log('6') ;
}



function onError() {
    alert("Could Not Connect to WebSocket! , REFRESH IT") ;
}

function sendMessage(event) {



    var senderNameInput = document.getElementById('SenderName') ;
    var messageContent = document.getElementById('SenderMessage').value.trim() ;

    // Funny cases - #TimePass
    if(senderNameInput.disabled === false && messageContent === '') {
            alert("Edho oka box ayina fill cheyi ra **papa...") ;
            return ;
    }

    if(senderNameInput.disabled === false) {
        alert("UserName rayakunda Message etla pamputav ra...") ;
        return ;
    }

    if(messageContent === '') {
            alert("Message la edho okati nimpute send avutadhi..") ;
            return ;
    }




    if(messageContent && stompClient) {
        var chatMessage = {
            sender : username ,
            content : messageContent ,
            messageType : 'CHAT' ,
            senderColor : userColor

        } ;

        stompClient.send('/app/chat.sendMessage' ,
                        {} ,
                         JSON.stringify(chatMessage)
        );

        document.getElementById('SenderMessage').value = '' ;


    }

    event.preventDefault() ;
}

console.log('1') ;

document.getElementById('nameBtn').onclick = connect ;
document.getElementById('SendMessage').onclick = sendMessage ;


