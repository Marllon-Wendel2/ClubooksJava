const apiBaseUrl = 'http://localhost:8080/book'; // URL da API
const livrosList = document.getElementById('livrosList');

// Função para carregar livros
function carregarLivros() {
    fetch(apiBaseUrl)
        .then(response => response.json())  // Converte a resposta em JSON
        .then(livros => {
            livrosList.innerHTML = ''; // Limpa a lista antes de adicionar os novos livros
            livros.forEach(livro => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <h3>${livro.title}</h3>
                    <p><span>Autor:</span> ${livro.autor}</p>
                    <p><span>Data de Criação:</span> ${livro.dataCriada}</p>
                    <p><span>Sinopse:</span> ${livro.sinopse}</p>
                    <div><span>Conteúdo:</span>
                        <ul>
                            ${livro.conteudo.map(capitulo => {
                                return `
                                    <li>
                                        <strong>${capitulo.capitulo} - ${capitulo.titulo}</strong>
                                        <p>${capitulo.texto}</p>
                                    </li>
                                `;
                            }).join('')}
                        </ul>
                    </div>
                `;
                livrosList.appendChild(li);
            });
        })
        .catch(error => console.error('Erro ao carregar livros:', error));
}

// Carregar livros quando a página for carregada
document.addEventListener('DOMContentLoaded', carregarLivros);
