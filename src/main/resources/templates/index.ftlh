<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith>

    <div class="flex flex-justify-content-center">
  <form>
      <nav>
        <a  href="/main"> Главная  </a>
        <a href="/addTheme">Создать тему</a>
        <input type="text" name="text" id="searchForm" placeholder="search" aria-controls="example">
        <div class="logins">
            <a  href="/register"> Регистрация </a>
            <a  href="/login"> Войти </a>
        </div>
      </nav>
  </form>


        <form  action="/logout" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>
            <button class="flex box-128 logout-button">Выйти </button>
        </form>

    </div>

    <div>
        <form>
        <#if cartItems??>
            <#list items as item>
                <p>${item.id} - ${item.text} - ${item.date} - - ${item.author}</p>
            </#list>
        <#else>
            <p>nothing!</p>
        </#if>

        </form>
    </div>

    <#include "partials/pagination.ftlh">

   </@main.renderWith>