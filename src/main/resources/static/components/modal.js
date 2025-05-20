import { api } from "/lib/api.js"; // Caminho absoluto a partir de static 

export function initModal() {
  const modal = document.getElementById("usuario-modal");
  const closeBtn = document.getElementById("fechar-modal-btn");
  const salvarBtn = document.getElementById("salvar-usuario-btn");

  let usuarioEditando = null;

  // Abrir modal
  window.abrirModalUsuario = (usuario = null) => {
    usuarioEditando = usuario;
    modal.classList.remove("hidden");
    document.body.style.overflow = "hidden";

    if (usuario) {
      preencherModal(usuario);
    } else {
      modal.querySelectorAll("input, select").forEach(el => el.value = "");
    }
  };

  // Fechar modal
  function fecharModal() {
    modal.classList.add("hidden");
    document.body.style.overflow = "";
  }

  closeBtn?.addEventListener("click", fecharModal);
  document.addEventListener("keydown", e => {
    if (e.key === "Escape") fecharModal();
  });

  // Salvar usuário
  salvarBtn?.addEventListener("click", async () => {
    const data = {
      nome: document.getElementById("modal-nome").value,
      cpf: document.getElementById("modal-cpf").value,
      numeroSus: document.getElementById("modal-sus").value,
      dataNascimento: document.getElementById("modal-nascimento").value,
      sexo: document.getElementById("modal-sexo").value,
      telefone: document.getElementById("modal-telefone").value.replace(/\D/g, ''),
      telCelular: document.getElementById("modal-celular").value.replace(/\D/g, ''),
      email: document.getElementById("modal-email").value,
      logradouro: document.getElementById("modal-logradouro").value,
      numero: document.getElementById("modal-numero").value,
      complemento: document.getElementById("modal-complemento").value,
      bairro: document.getElementById("modal-bairro").value,
      cep: document.getElementById("modal-cep").value,
      cidade: document.getElementById("modal-cidade").value,
      uf: document.getElementById("modal-uf").value,
    };

    try {
      let resposta;
      if (usuarioEditando?.id) {
        resposta = await api.put(`/usuarios/${usuarioEditando.id}`, data);
        alert("Usuário atualizado com sucesso!");
      } else {
        resposta = await api.post("/usuarios", data);
        alert("Usuário criado com sucesso!");
      }

      fecharModal();
      if (window.preencherCampos && typeof window.preencherCampos === 'function') {
        window.preencherCampos(resposta); // <-- Atualiza tela principal
      }
    } catch (err) {
      console.error("Erro ao salvar:", err);
      alert("Erro ao salvar. Verifique os dados.");
    }
  });

  // Preencher dados no modal
  function preencherModal(usuario) {
    const map = {
      nome: "modal-nome",
      cpf: "modal-cpf",
      numeroSus: "modal-sus",
      dataNascimento: "modal-nascimento",
      sexo: "modal-sexo",
      telefone: "modal-telefone",
      telCelular: "modal-celular",
      email: "modal-email",
      logradouro: "modal-logradouro",
      numero: "modal-numero",
      complemento: "modal-complemento",
      bairro: "modal-bairro",
      cep: "modal-cep",
      cidade: "modal-cidade",
      uf: "modal-uf"
    };

    Object.entries(map).forEach(([k, id]) => {
      const el = document.getElementById(id);
      if (el) el.value = usuario[k] || "";
    });
  }

  // Máscaras
  function aplicarMascaras() {
    const mask = (el, fn) => el?.addEventListener("input", () => el.value = fn(el.value));
    const mascaraCPF = v => v.replace(/\D/g, '').slice(0, 11).replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    const mascaraTel = v => v.replace(/\D/g, '').slice(0, 11).replace(/(\d{2})(\d)/, '($1) $2').replace(/(\d{5})(\d)/, '$1-$2');
    const mascaraCEP = v => v.replace(/\D/g, '').slice(0, 8).replace(/(\d{5})(\d{0,3})/, '$1-$2');

    mask(document.getElementById("modal-cpf"), mascaraCPF);
    mask(document.getElementById("modal-telefone"), mascaraTel);
    mask(document.getElementById("modal-celular"), mascaraTel);
    mask(document.getElementById("modal-cep"), mascaraCEP);
  }

  aplicarMascaras();
}
