
window.addEventListener('load', function () {

    let button = document.getElementById("searchId");
    button.addEventListener("click", function (e) {
        e.preventDefault();
        let searchForm = document.getElementById("login-form");
        let data = new FormData(searchForm);
        data.get(name);
        const dataJSON = JSON.stringify(Object.fromEntries(data));

        console.log(dataJSON)
        fetch("http://localhost:8080/theme" + dataJSON , {
            method: "GET"
        }).then(response => {
            console.log(response.data,"++++++++++++++++=")
        })


    });

});

const getCurrentPage = () => {
    const loc = (typeof window.location !== 'string') ? window.location.search : window.location;
    const index = loc.indexOf('page=');
    return index === -1 ? 1 : parseInt(loc[index + 5]) + 1;
};

const constructGetUrl = (url, queryParams) => {
    for (let key in queryParams) {
        if (queryParams.hasOwnProperty(key)) {
            console.log(key, queryParams[key]);
        }
    }
    // TODO
};

(function loadPlacesPageable() {

    const placeTemplate = (listItem) => {
        const template = ` <form>
        <#if cartItems??>
            <#list items as item>
                <p>${item.id} - ${item.text} - ${item.date} - - ${item.author}</p>
            </#list>
        <#else>
            <p>nothing!</p>
        </#if>

        </form>
        `;

        const elem = document.createElement('div');
        elem.innerHTML = template.trim();

        // return inner div with classes flex etc
        return elem.children[0];
    };

    const fetchPlaces = async (page, size) => {
        const placesPath = `${serverPath}/products?page=${page}&size=${size}`;
        const data = await fetch(placesPath, {cache: 'no-cache'});
        return data.json();
    };

    const loadNextPlacesGenerator = (loadNextElement, page) => {
        return async (event) => {
            event.preventDefault();
            event.stopPropagation();

            const defaultPageSize = loadNextElement.getAttribute('data-default-page-size');
            const data = await fetchPlaces(page, defaultPageSize);

            loadNextElement.hidden = data.length === 0;
            if (data.length === 0) {
                return;
            }

            const list = document.getElementById('itemList');
            for (let item of data) {
                list.append(placeTemplate(item));
            }

            loadNextElement.addEventListener('click', loadNextPlacesGenerator(loadNextElement, page + 1), {once: true});
            window.scrollTo(0, document.body.scrollHeight);
        };
    };

})();


