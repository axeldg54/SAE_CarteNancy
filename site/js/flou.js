export function show() {
    let form = document.getElementById('form');
    form.classList.remove('formOut');
    form.classList.add('formIn');
    form.style.bottom = '25%';
    let flou = document.getElementById('flou');
    flou.style.display = 'block';
    flou.addEventListener('click', () => {
        hide();
    });
}

export function hide() {
    let form = document.getElementById('form');
    form.classList.remove('formIn');
    form.classList.add('formOut');
    form.style.bottom = '-100%';
    let flou = document.getElementById('flou');
    flou.style.display = 'none';
    flou.removeEventListener('click', hide);
}