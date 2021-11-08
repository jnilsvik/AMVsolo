<html>
<head>
    <title>Title</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style> .page {
        flex-grow: 1;
        height: 100%;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        padding-bottom: 70px;
    }
    article{
        width: 480px;
        background: #fff;
        box-shadow: 0 25px 75px rgba(16, 30, 54, .25);
        border-radius: 6px;
        padding: 30px 60px 26px;
        margin-top: -75px;
    }
    body{
        background-image: url(https://media.discordapp.net/attachments/472062607646261249/702987431653277705/unknown.png);
        background-repeat: no-repeat;
        background-attachment: fixed;
        background-size: 100% 100%;
    }
    button {
        width: 100%;
    }</style>
</head>
<body>
<div class="page">
    <article class="my-3" id="floating-labels"> <!-- this is temp to genter-->
        <div class="bd-heading sticky-xl-top align-self-start mt-5 mb-3 mt-xl-0 mb-xl-2">
            <h3>Forgot password</h3>
        </div>

        <div>
            <div class="bd-example">
                <form action="">
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="nPassword1" placeholder="Password">
                        <label for="nPassword1">New password</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="nPassword2" placeholder="Password">
                        <label for="nPassword2">Confirm</label>
                    </div>
                    <div class="col-12">
                        <button class="btn btn-primary" type="submit">Submit</button>
                    </div>
                    <div style="text-align:center">
                        <a href='register'>Don't have an account? Register here!</a>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>