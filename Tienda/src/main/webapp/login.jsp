<%-- 
    Document   : loging
    Created on : 28/02/2023, 01:35:25
    Author     : luis
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Required meta tags -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- css -->
        <link href="resources/css/signin.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="text-center">
        <main class="form-signin">
            <form method="POST" action="Login">
                <img class="mb-4" src="resources/img/store.png" alt="" width="72" height="72">
                <h1 class="h3 mb-3 fw-normal">Iniciar sesión</h1>
                <h4 class="text-warning">${msj}</h4>
                <div class="form-floating">
                    <input type="text" name="username" class="form-control" id="floatingInput" placeholder="Nombre de usuario">
                    <label for="floatingInput">Nombre de usuario</label>
                </div>
                <div class="form-floating">
                    <input type="password" name="pass" class="form-control" id="floatingPassword" placeholder="Password">
                    <label for="floatingPassword">Contraseña</label>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit">Ingresar</button>
            </form>
        </main>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>