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
    var passwordLength = document.getElementById("passwordLength").value;
    if (!passwordLength) {
      alert("Please enter password length");
      return;
    }
    
    var applet = document.getElementById("myApplet");
    var password = applet.generatePassword(passwordLength);
    document.getElementById("passwordOutput").innerHTML = password;
  }

  function addpassword(){
    let _passname = document.getElementById("passname").value
    let _password = document.getElementById("password").value
    let _url = document.getElementById("url").value

    var x = document.getElementById("snackbar");
    fetch('http://localhost:8085/user/savePassword  ', {
        method: 'post',
        body: JSON.stringify({
            passName: _passname,
            password: _password,
            url : _url,

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
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
})
  }