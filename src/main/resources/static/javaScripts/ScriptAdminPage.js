let inputGenre = document.querySelector('.genre-input');
let areaForSelect = document.querySelector('.select-genre');
let areaForSelectAuthor = document.querySelector('.select-author');
let areaForSelectVoice = document.querySelector('.select-voice');
let result = document.querySelector('.result');
let counterGenre = 0;
//Обработка событий из ввода жанров;
function selectGenre(elem){
    let newSelectGenre =
        `<li class="genre-el-select" 
            title="${elem.getAttribute('title')}"
            onclick="deleteGenre(this)">
            ${elem.innerText}<img src="../icons/delete.svg" alt="delete" class="iconDelete">
         </li>`
    if(areaForSelect.innerHTML.search(elem.innerText) == -1) {
        areaForSelect.innerHTML += newSelectGenre;
        result.innerHTML +=
            `<input type="checkbox" name="genres" class="hide" 
                value="${elem.getAttribute('title')}" checked>`;
    }
}
function deleteGenre(elem){
    elem.classList.add('hide');
    elem.innerHTML = '';
    let allGenre= result.querySelectorAll(
        `[value="${elem.getAttribute("title")}"]`);
    allGenre.forEach(function(elem){
        elem.removeAttribute('checked')
    });
}
document.querySelector('.genre-input').oninput = function(){
    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('.elastic-genre div');
    if(val != ''){
        elasticItems.forEach(function(elem){
            if(elem.innerText.search(val) == -1){
                elem.classList.add('hide');
            }
            else {
                elem.classList.remove('hide');
            }
        });
    } else {
        elasticItems.forEach(function (elem){
            elem.classList.remove('hide');
            elem.innerHTML = elem.innerText;
        });
    }
}
//Обработка событий из ввода авторов;
function selectAuthor(elem){
    let newSelectAuthor =
        `<li class="author-el-select"
            title="${elem.getAttribute('title')}"
            onclick="deleteAuthor(this)">
            ${elem.innerHTML} <img src="../icons/delete.svg" alt="delelte" class="iconDelete">    
        </li>`
    if(areaForSelectAuthor.innerHTML.search(elem.innerText) == -1){
        areaForSelectAuthor.innerHTML += newSelectAuthor;
        result.innerHTML +=
            `<input type="checkbox" name="authors" class="hide" 
                value="${elem.getAttribute('title')}" checked>`;
    }
}
function deleteAuthor(elem){
    elem.classList.add('hide');
    elem.innerHTML = '';
    let allAuthor= result.querySelectorAll(
        `[value="${elem.getAttribute("title")}"]`);
    allAuthor.forEach(function(elem){
        elem.removeAttribute('checked')
    })
}
document.querySelector('.author-input').oninput = function(){
    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('.elastic-author div');
    if(val != ''){
        elasticItems.forEach(function(elem){
            if(elem.innerText.search(val) == -1){
                elem.classList.add('hide');
            }
            else {
                elem.classList.remove('hide');
            }
        });
    } else {
        elasticItems.forEach(function (elem){
            elem.classList.remove('hide');
            elem.innerHTML = elem.innerText;
        });
    }
}
//Обработка событий из ввода чтецов
function selectVoice(elem){
    let newSelectVoice =
        `<li class="voice-el-select"
        title="${elem.getAttribute('title')}"
        onclick="deleteVoice(this)">
        ${elem.innerHTML} <img src="../icons/delete.svg" art="delete" class="iconDelete">
        </li>`
    if(areaForSelectVoice.innerHTML.search(elem.innerText) == -1){
        areaForSelectVoice.innerHTML += newSelectVoice;
        result.innerHTML +=
            `<input type="checkbox" name="voices" class="hide" 
                value="${elem.getAttribute('title')}" checked>`;
    }
}
function deleteVoice(elem){
    elem.classList.add('hide');
    elem.innerHTML = '';
    let allVoice= result.querySelectorAll(
        `[value="${elem.getAttribute("title")}"]`);
    allVoice.forEach(function(elem){
        elem.removeAttribute('checked')
    })
}
document.querySelector('.voice-input').oninput = function(){
    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('.elastic-voice div');
    if(val != ''){
        elasticItems.forEach(function(elem){
            if(elem.innerText.search(val) == -1){
                elem.classList.add('hide');
            }
            else {
                elem.classList.remove('hide');
            }
        });
    } else {
        elasticItems.forEach(function (elem){
            elem.classList.remove('hide');
            elem.innerHTML = elem.innerText;
        });
    }
}
//результаты выборки

//Блок с удалением книги
document.querySelector('.delete-book-input').oninput = function(){
    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('.elastic-delete-block .elastic-el');
    if(val != ''){
        elasticItems.forEach(function(elem){
            // console.log(
            //     elem.innerHTML);
            if(elem.innerHTML.search(val) == -1){
                elem.classList.add('hide');
                // elem.querySelector('.card-book').classList.add('hide');
                // elem.querySelector('.card-book .cad')
            }
            else {
                elem.classList.remove('hide');
            }
        });
    } else {
        elasticItems.forEach(function (elem){
            elem.classList.remove('hide');
        });
    }
}
function deleteBook(elem){
    let areaDeleteBook = document.querySelector('.delete-book-res');
    areaDeleteBook.innerHTML +=
        `<form method="post" action="AdminPage/deleteBook">
            <input type="number" name="idBook" value="${elem.getAttribute('title')}">
            <button class="deleteBookId" type="submit"></button>
        </form>`
    let inputDeleteBook = document.querySelector('.deleteBookId');
    console.log(inputDeleteBook);
    inputDeleteBook.click();
}

