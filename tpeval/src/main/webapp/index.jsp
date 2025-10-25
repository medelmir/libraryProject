<!DOCTYPE html>
<meta charset="UTF-8">
<html>
    <head>
        <title>Page d'accueil de la bibliothèque</title>
    <style>
        body{
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            margin: 0;
            padding: 20px;
        }
    h1{
            font-size: 36px;
            font-weight: bold;
            text-align: center;
            color: #333333;
            margin-bottom: 40px;
    }
    p{
            font-size: 18px;
            margin: 10px 0;
            text-align: center;
        }
    a{
            text-decoration: none;
            color: #000000;
            display: grid;
            padding: 10px 15px;
            border: 2px solid #000000;
            text-align: center;
        }
    a:hover{
            background-color: #ecad1d;
            color: #ffffff;
        }
    .background-image img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
        z-index: -1;
        opacity: 0.3;
        filter: blur(2px);
    }
    </style>
</head>
<body>
    <div class="background-image">
        <img src="https://expodif.fr/wp-content/uploads/2022/04/Bibliotheque-de-livres.png" alt="">
    </div>
        <h1>Bienvenue à la bibliothèque</h1>
    <p>
        <a href="catalog">Voir le catalogue des livres</a>
    </p>
    <p>
        <a href="borrowed">Voir mes livres empruntés</a>
    </p>
    <p>
        <a href="checkout">checkout</a>
    </p>
    
    
</body>
</html>