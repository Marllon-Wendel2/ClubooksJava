const API_URL = 'http://localhost:8080/book'; // Substitua pela URL da sua API

document.addEventListener('DOMContentLoaded', () => {
    carregarLivros();
    const form = document.getElementById('add-book-form');
    form.addEventListener('submit', salvarLivro);
});

// Função para carregar livros
async function carregarLivros() {
    try {
        const response = await fetch(`${API_URL}`);
        if (!response.ok) throw new Error('Erro ao carregar livros');
        const livros = await response.json();
        exibirLivros(livros);
    } catch (error) {
        console.error(error);
        alert('Erro ao carregar livros');
    }
}

// Função para salvar um livro
async function salvarLivro(event) {
    event.preventDefault();
    const title = document.getElementById('title').value;
    const author = document.getElementById('author').value;
    const description = document.getElementById('description').value;

    const livro = { title, author, description };
    try {
        const response = await fetch(`${API_URL}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(livro)
        });
        if (!response.ok) throw new Error('Erro ao salvar livro');
        alert('Livro salvo com sucesso');
        carregarLivros();
    } catch (error) {
        console.error(error);
        alert('Erro ao salvar livro');
    }
}

// Função para exibir livros na tela
function exibirLivros(livros) {
    const booksList = document.getElementById('books');
    booksList.innerHTML = '';
    livros.forEach(livro => {
        const li = document.createElement('li');
        li.textContent = `${livro.title} por ${livro.author}`;
        booksList.appendChild(li);
    });
}
