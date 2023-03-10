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
        location.href = '2fa.html';
}
})
}