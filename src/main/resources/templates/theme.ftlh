<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith>

    <form action="/theme/add" method="post" id="theme-form">
    <div>
        <label for="value">theme </label>
        <input name="value" id="value">
        <a href="" id="theme-add" >Create</a>
    </div>
   </form>

    <script>
        'use strict';

        window.addEventListener('load', function () {


            async function saveTheme(e) {
                e.preventDefault();
                e.stopPropagation();

                const form = document.getElementById("theme-form");
                const options = {
                    method : 'post',
                    body: new FormData(form)
                };

                await fetch("/add/theme", options);
            }

            fetch("http://localhost:8080/theme",{
                method: "GET"
            }).then(res => res.json()).then(res => console.log(res))

            const updateElement = document.getElementById("sms-add");
            updateElement.addEventListener('click', saveTheme);

        })

    </script>

</@main.renderWith>