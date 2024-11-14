function buscarLivros() {
    const livro = document.getElementById("livroInput").value.replace(" ", "_"); // Substitui espaços por "_"
    const url = `http://localhost:8080/book/procurarlivroexterno/${livro}`; // URL corrigida com o prefixo '/book'

    console.log("URL da requisição:", url);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar livros. Tente novamente mais tarde.');
            }
            return response.json();
        })
        .then(data => {
            console.log("Resposta da API:", data);
            mostrarLivros(data);
        })
        .catch(error => {
            console.error("Error:", error);
            alert(error.message);
        });
}

function mostrarLivros(data) {
    const resultadosDiv = document.getElementById("resultados");
    resultadosDiv.innerHTML = ""; // Limpar resultados anteriores

    if (data.items && data.items.length > 0) {
        data.items.forEach(item => {
            const livro = item.volumeInfo;
            const livroDiv = document.createElement("div");
            livroDiv.classList.add("livro");

            const img = livro.imageLinks ? `<img src="${livro.imageLinks.capa}" alt="${livro.titulo}" />` : "";
            const descricao = livro.descricao ? `<p>${livro.descricao}</p>` : "<p>Sem descrição disponível.</p>";

            livroDiv.innerHTML = `
                ${img}
                <h3>${livro.titulo}</h3>
                ${descricao}
                <p><strong>Autor(es):</strong> ${livro.autores.join(", ")}</p>
                <p><strong>Data de Publicação:</strong> ${livro.dataPublicacao}</p>
                <a href="${livro.previa}" target="_blank">Ver mais</a>
            `;

            resultadosDiv.appendChild(livroDiv);
        });
    } else {
        resultadosDiv.innerHTML = "<p>Nenhum livro encontrado.</p>";
    }
}
