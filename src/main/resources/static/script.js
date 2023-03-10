function register() {



    let _email = document.getElementById("email").value
    let _firstName = document.getElementById("firstName").value
    let _lastName = document.getElementById("lastName").value
    let _password = document.getElementById("password").value
    let _location = document.getElementById("location").value
    let _number = document.getElementById("number").value


    fetch('http://localhost:8085/user/signup', {
        method: 'post',
        body: JSON.stringify({
            email: _email,
            password: _password,
            firstName: _firstName,
            lastName: _lastName,
            location: _location,
            phoneNumber: _number,
   
            isActive: false //default
        }),
        headers: new Headers({'content-type': 'application/json',
                            'authorization': 'Bearer ',
                        }),
        }
    )
    .then( async (response) => {

    // get json response here
    let data = await response.json();
    console.log(response.status)
    if(response.status === 200){
        location.href = 'login.html';
}
})
}


function login(){
    let _email = document.getElementById("email").value
    let _password = document.getElementById("password").value

    fetch('http://localhost:8085/user/login', {
        method: 'post',
        body: JSON.stringify({
            email: _email,
            password: _password,

            isActive: true //default
        }),
        headers: new Headers({'content-type': 'application/json',
                            'authorization': 'Bearer ',
                        }),
        }
    )
    .then( async (response) => {

    // get json response here
    let data = await response.json();
    console.log(response.status)
    if(response.status === 200){
        location.href = '2fa.html';
}
})
}


function authenticate(){

    let _auth = document.getElementById("auth").value

    fetch('http://localhost:8085/user/login-2fa', {
        method: 'post',
        body: JSON.stringify({
            otp: _auth,

            isActive: true //default
        }),
        headers: new Headers({'content-type': 'application/json',
                            'authorization': 'Bearer ',
                        }),
        }
    )
    .then( async (response) => {

    // get json response here
    let data = await response.json();
    console.log(response.status)
    if(response.status === 200){
        location.href = 'index.html';
        sessionStorage.setItem("token", data['token'])
}
})

}

function generateAndDisplayPassword() {
    let _len = document.getElementById("len").value

    var myHeaders = new Headers();
myHeaders.append("Authorization", "Bearer " + sessionStorage.getItem("token"));
myHeaders.append("Content-Type", "application/json");
myHeaders.append("Cookie", "JSESSIONID=64698DE54999A39FAB6B31B611ED8758");
myHeaders.append("Access-Control-Allow-Origin", "http://localhost:8085");
myHeaders.append('Access-Control-Allow-Methods', 'POST');
// 'Access-Control-Allow-Origin':'*',
//                 'Access-Control-Allow-Methods':'POST'

var raw = JSON.stringify({
  "length": _len
});

var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: raw,
  redirect: 'follow'
};

fetch("http://localhost:8085/passStorage/generatePass", requestOptions)
  .then( async response => {
    let data = await response.text();
    console.log(response.status)
    if(response.status === 200){
        let output = document.getElementById("passwordOutput")
        output.appendChild(document.createTextNode(data));
    }
  })
  .catch(error => console.log('error', error));
}
  

  function addpassword(){
    let _passname = document.getElementById("passname").value
    let _password = document.getElementById("password").value
    let _url = document.getElementById("url").value

    var x = document.getElementById("snackbar");
    fetch('http://localhost:8085/passStorage/save', {
        method: 'post',
        body: JSON.stringify({
            passName: _passname,
            password: _password,
            url : _url,

            isActive: true //default
        }),
        headers: new Headers({'content-type': 'application/json',
                            'authorization': 'Bearer ' + sessionStorage.getItem("token"),
                        }),
        }
    )
    .then( async (response) => {

    // get json response here
    let data = await response.json();
    console.log(response.status)
    if(response.status === 200){
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
})
  }






  function findMyPasses(){
    fetch('http://localhost:8085/passStorage/myPass', {
        method: 'get',
        headers: new Headers({'content-type': 'application/json',
                            'Authorization': 'Bearer ' + sessionStorage.getItem('loginToken'),
    

                        }),
        }, 
    )
    .then( async (response) => {

// get json response here
let data = await response.json();
console.log(response.status);
console.log(data);
if(response.status === 200){
//    console.log(data);

    let jobApplicationElement = document.getElementById("job_applications");
    console.log(data);
    for(let i = 0; i < data.length; i++){
        console.log(data[i]["id"]);
        let div = document.createElement("div");
        div.setAttribute("id", "application" + data[i]["id"]);
        const employer = document.createElement("p");
        const line = document.createElement("br");
        const node = document.createElement("p");
        const node2 = document.createElement("p");
        const button1 = document.createElement("button");
        const comment = document.createElement("button");
        const AddComment = document.createElement("input");
        const addCommentBtn = document.createElement("button");
        button1.setAttribute("id", data[i]["id"]);
            button1.setAttribute("onclick", "volunteer(this.id)");
        div.setAttribute("class", "app");

//        <a href="commentsCodevil.html">comments</a>
//        comment.setAttribute("id", "comments");
        let currentId = data[i]["id"];
//        comment.setAttribute("onclick", "showComments(this.currentId)");
        comment.setAttribute("onclick", "showComments(" + currentId + ")");
        AddComment.setAttribute("type", "text");
        AddComment.setAttribute("id", "text" + currentId);
        addCommentBtn.setAttribute("onclick", "addComment(" + currentId + "), location.href = 'startPageCodevil.html'");
        button1.setAttribute("class", "blank");
        comment.setAttribute("class", "blank");
        AddComment.setAttribute("class", "blank");
        addCommentBtn.setAttribute("class", "blank");
        employer.setAttribute("class", "title1");
        node.setAttribute("class", "title1");
        node2.setAttribute("class", "text1");

        div.appendChild(employer);
        div.appendChild(node);
        div.appendChild(line);
        div.appendChild(node2);
        div.appendChild(line);
        div.appendChild(button1);
        div.appendChild(line);
        div.appendChild(comment);
        div.appendChild(line);
        div.appendChild(AddComment);
        div.appendChild(line);
        div.appendChild(addCommentBtn);
        div.appendChild(line);
        jobApplicationElement.appendChild(div);


//        if(data[i]["volunteer_id"] === null){
//                    button1.appendChild(document.createTextNode("Apply for this Job App"));
//                    button1.setAttribute("onclick", "volunteer(this.id)");
//                }
//                else{
//                    button1.appendChild(document.createTextNode("There is already volunteer for this job"));
//                }

        employer.appendChild(document.createTextNode(data[i]["employer"]["username"] + "'s Job Application"));
        button1.appendChild(document.createTextNode("Apply for this Job App"));
        node.appendChild(document.createTextNode(data[i]["title"]));
        node2.appendChild(document.createTextNode(data[i]["text"]));
        comment.appendChild(document.createTextNode("comments"));
        AddComment.appendChild(document.createTextNode("comments"));
        addCommentBtn.appendChild(document.createTextNode("Add Comment"));

        jobApplicationElement.append(div);

    }
}


})
}

function getUser(){

    var x = document.getElementById("snackbar");
    fetch('http://localhost:8085/user/userContext', {
        method: 'get',
        headers: new Headers({'content-type': 'application/json',
                            'authorization': 'Bearer ' + sessionStorage.getItem("token"),
                        }),
        }
    )
    .then( async (response) => {

    // get json response here
    let data = await response.text();
    console.log(response.status)
    if(response.status === 200){
        let userContext = document.getElementById("user")
        userContext.appendChild(document.createTextNode(data));
}
})
  }

