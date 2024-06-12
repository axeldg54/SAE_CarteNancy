export function show() {
    let flou = document.getElementById('flou');
    flou.style.display = 'block';
    document.getElementById('form').style.left = "25%";
    flou.addEventListener('click', () => {
        hide();
    });
}

function hide() {
    let form = document.getElementById('form');
    form.style.left = "-100%";
    let flou = document.getElementById('flou');
    flou.style.display = 'none';
    flou.removeEventListener('click', hide);

}