<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 04.11.2021
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
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
            <h3>Register</h3>
        </div>

        <div>
            <div class="bd-example">
                <form action="">
                    <!--
                    picture path thingy goes here, but we need to upload the pic from here
                    will also need to assign the path somehow?
                    -->
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="tName" placeholder="Thingy X">
                        <label for="tName">Tool name</label>
                    </div>
                    <div class="form-floating mb-3">
                    <!--
                    need to load in the tool categories here here
                    -->
                    </div>
                    <div class="form-floating mb-3">
                    <!--
                    need to load in the certificates here
                    -->
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="tFPrice" placeholder="0kr">
                        <label for="tFPrice">Initial price</label>
                        <span class="input-group-text">kr</span>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="tAPrice" placeholder="0kr">
                        <label for="tAPrice">Price afterwards</label>
                        <span class="input-group-text">kr</span>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="tDesc" placeholder="This tool...">
                        <label for="tDesc">Tool description</label>
                    </div>
                    <div class="col-12">
                        <button class="btn btn-primary" type="submit">Login</button>
                    </div>
                    <div style="text-align:center">
                        <a href='login'>Already have an account? Login here!</a>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>