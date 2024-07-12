let resultInputTag = document.querySelector('.result-select-input');
//переменные для блока с выбором жанра
let textareaGenre = document.querySelector('.textarea-genre');
let elasticGenre = document.querySelectorAll('.filter-genre li')
let resultGenreSelect = document.querySelector('.result-genre-select');
//переменные для блока с выбором авторов
let textareaAuthor = document.querySelector('.textarea-author');
let elasticAuthor = document.querySelectorAll('.filter-author li');
let resultAuthorSelect = document.querySelector('.result-author-select');
//переменные для блока с выбором чтеца
let textareaVoice = document.querySelector('.textarea-voices');
let elasticVoice = document.querySelector('.filter-voices');
let resultVoiceSelect = document.querySelector('.result-voice-select');
//Выбор жанра
textareaGenre.oninput = function(){
    let val = this.value.trim();
    if(val != ' '){
        elasticGenre.forEach(function(elem){
            if(elem.innerText.search(val) == -1)
                elem.classList.add('hide');
            else
                elem.classList.remove('hide');
        });
    } else
        elasticGenre.forEach((elem) => elem.classList.remove('hide'));
}
function clickGenre(elem){
    let resultGenre = document.querySelectorAll('.result-genre-select div');
    let isAgain = false;
    resultGenre.forEach(function(item){
        if(elem.getAttribute('title') == item.getAttribute('title'))
            isAgain = true;
    })
    if(!isAgain){
        let newGenreElement =
            `<div class="genre-element"
                onclick="deleteSelectGenre(this)"
                title="${elem.getAttribute('title')}">
            ${elem.innerText} 
           <img src="../icons/delete.svg" alt="delete" class="iconDelete">
        </div>`;
        let selectInputGenre =
            `<input name="genres"
            type="checkbox" 
            value="${elem.getAttribute('title')}" 
            class="hide"
            style="position:absolute;"
            checked>`
        resultInputTag.innerHTML += selectInputGenre;
        resultGenreSelect.innerHTML += newGenreElement;
    }
}
function deleteSelectGenre(elem){
    let resultInputTagAll = document.querySelectorAll('.result-select-input input');
    elem.classList.add('hide');
    elem.setAttribute('style', 'position:absolute');
    resultInputTagAll.forEach(function (elemIn){
        if(elemIn.getAttribute('value') == elem.getAttribute('title')){
            elemIn.removeAttribute('value');
            elemIn.removeAttribute('checked');
            elem.setAttribute('title', null);
        }
    });
}
// ----------------------------
//Выбор автора
textareaAuthor.oninput = function(){
    let val = this.value.trim();
    if(val != ' '){
        elasticAuthor.forEach(function(elem){
            if(elem.innerText.search(val) == -1)
                elem.classList.add('hide');
            else
                elem.classList.remove('hide')
        })
    }else
        elasticAuthor.forEach((elem) => elem.classList.remove('hide'));

}
function clickAuthor(elem){
    let resultAuthor = document.querySelectorAll('.result-author-select div');
    let isAgain = false;
    resultAuthor.forEach(function(item){
        if(elem.getAttribute('title') == item.getAttribute('title'))
            isAgain = true;
    })
    if(!isAgain) {
        let newAuthorElement =
            `<div class="author-element"
                onclick="deleteSelectAuthor(this)"
                title="${elem.getAttribute('title')}">
            ${elem.innerText} 
           <img src="../icons/delete.svg" alt="delete" class="iconDelete">
        </div>`;
        let selectInputAuthor =
            `<input name="authors"
            type="checkbox" 
            value="${elem.getAttribute('title')}" 
            class="hide"
            style="position:absolute;"
            checked>`
        resultInputTag.innerHTML += selectInputAuthor;
        resultAuthorSelect.innerHTML += newAuthorElement;
    }
}
function deleteSelectAuthor(elem){
    let resultInputTagAll = document.querySelectorAll('.result-select-input input');
    elem.classList.add('hide');
    elem.setAttribute('style', 'position:absolute');
    resultInputTagAll.forEach(function (elemIn){
        if(elemIn.getAttribute('value') == elem.getAttribute('title')){
            elemIn.removeAttribute('value');
            elemIn.removeAttribute('checked');
            elem.setAttribute('title', null);
        }
    });
}
//------------------------------
//Выбор чтеца
function clickVoice(elem){
    let resultVoice = document.querySelectorAll('.result-voice-select div');
    let isAgain = false;
    resultVoice.forEach(function(item){
        if(elem.getAttribute('title') == item.getAttribute('title'))
            isAgain = true;
    })
    if(!isAgain) {
        let newVoiceElement =
            `<div class="voice-element"
                onclick="deleteSelectVoice(this)"
                title="${elem.getAttribute('title')}">
            ${elem.innerText} 
           <img src="../icons/delete.svg" alt="delete" class="iconDelete">
        </div>`;
        let selectInputVoice =
            `<input name="voices"
            type="checkbox" 
            value="${elem.getAttribute('title')}" 
            class="hide"
            style="position:absolute;"
            checked>`
        resultInputTag.innerHTML += selectInputVoice;
        resultVoiceSelect.innerHTML += newVoiceElement;
    }
}
function deleteSelectVoice(elem){
    let resultInputTagAll = document.querySelectorAll('.result-select-input input');
    elem.classList.add('hide');
    elem.setAttribute('style', 'position:absolute');
    resultInputTagAll.forEach(function (elemIn){
        if(elemIn.getAttribute('value') == elem.getAttribute('title')){
            elemIn.removeAttribute('value');
            elemIn.removeAttribute('checked');
            elem.setAttribute('title', null);
        }
    });
}