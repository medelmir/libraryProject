<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Page d'accueil de la bibliothèque</title>
    <style>
        /* Styles généraux */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 20px;
            color: #333333;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        h1 {
            font-size: 36px;
            font-weight: bold;
            text-align: center;
            color: #333333;
            margin-bottom: 40px;
            z-index: 10;
        }
        p {
            font-size: 18px;
            margin: 15px 0;
            text-align: center;
            width: 100%;
            max-width: 400px;
            z-index: 10;
        }
        
        /* Style des liens (boutons) */
        a {
            text-decoration: none;
            color: #333333;
            display: block; /* Changé de grid à block */
            padding: 15px 20px;
            border: 2px solid #333333;
            text-align: center;
            transition: background-color 0.3s, color 0.3s, border-color 0.3s;
            border-radius: 8px; /* Ajout de coins arrondis */
            font-weight: bold;
        }
        a:hover {
            background-color: #ecad1d; /* Jaune doré (plus stylé) */
            color: #ffffff;
            border-color: #ecad1d;
        }
        
        /* Style de l'image de fond */
        .background-image img {
            position: fixed; /* Mieux que absolute pour le scroll */
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
        <img src="https://expodif.fr/wp-content/uploads/2022/04/Bibliotheque-de-livres.png" alt="Arrière-plan Bibliothèque">
    </div>
        <h1>Bienvenue à la bibliothèque</h1>
    
    <p>
        <a href="catalog">Voir le catalogue des livres</a>
    </p>
    <p>
        <a href="borrowed">Voir mes livres empruntés</a>
    </p>
    <p>
        <a href="checkout">Checkout / Finaliser la commande</a>
    </p>
</body>
</html>