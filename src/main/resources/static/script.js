import {api} from './lib/api.js';
import { initModal } from './components/modal.js';

// Máscara para CPF (usada apenas no formulário principal)
function aplicarMascaraCPF(input) {
    let valor = input.value.replace(/\D/g, '');
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    input.value = valor.slice(0, 14);
}

// Máscara para Telefone/Celular (XX) XXXXX-XXXX
function aplicarMascaraTelefoneCelular(input) {
    let valor = input.value.replace(/\D/g, '');
    valor = valor.replace(/(\d{2})(\d)/, '($1) $2');
    valor = valor.replace(/(\d{5})(\d)/, '$1-$2');
    input.value = valor.slice(0, 15);
}

// Função para formatar telefone/celular ao exibir
function formatarTelefoneCelular(valor) {
    if (!valor) return '';
    let numeros = valor.replace(/\D/g, '');
    if (numeros.length < 10) {
        console.warn(`Número de telefone/celular incompleto: ${numeros}`);
        return numeros;
    }
    return numeros.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
}

// Função para buscar agendamentos por nome do usuário
async function buscarAgendamentosPorNome(nome) {
    const tabelaAgendamentos = document.getElementById('tabela-agendamentos-usuario');
    if (!tabelaAgendamentos) {
        console.warn('Elemento #tabela-agendamentos-usuario não encontrado no DOM. Ignorando busca de agendamentos.');
        return;
    }

    try {
        console.log(`Buscando agendamentos para: /agendamentos/nome?nome=${encodeURIComponent(nome)}`);
        const agendamentos = await api.get(`/agendamentos/nome?nome=${encodeURIComponent(nome)}`, { timeout: 5000 });
        console.log('Agendamentos recebidos:', agendamentos);

        const tbody = tabelaAgendamentos.querySelector('tbody') || tabelaAgendamentos.appendChild(document.createElement('tbody'));
        tbody.innerHTML = '';

        if (!agendamentos || agendamentos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5">Nenhum agendamento encontrado</td></tr>';
            return;
        }

        agendamentos.sort((a, b) => {
            const dataA = new Date(`${a.data}T${a.horario}`);
            const dataB = new Date(`${b.data}T${b.horario}`);
            return dataA - dataB;
        });

        agendamentos.forEach(agendamento => {
            const [ano, mes, dia] = agendamento.data.split('-');
            const dataFormatada = `${dia}/${mes}/${ano}`;
            const [hora, minuto] = agendamento.horario.split(':');
            const horarioFormatado = `${hora}:${minuto}h`;
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${agendamento.usuario.nome}</td>
                <td>${dataFormatada}</td>
                <td>${horarioFormatado}</td>
                <td>${agendamento.especialidade.nome}</td>
                <td><a href="#" class="alterar-link" data-agendamento-id="${agendamento.id}">[Alterar]</a></td>
            `;
            tbody.appendChild(tr);
            console.log('Linha adicionada:', tr.outerHTML);
        });

        const alterarLinks = tabelaAgendamentos.querySelectorAll('.alterar-link');
        console.log(`Encontrados ${alterarLinks.length} links [Alterar]`);
        alterarLinks.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const agendamentoId = link.getAttribute('data-agendamento-id');
                console.log('Clicou em [Alterar], ID:', agendamentoId);
                abrirModalEditarAgendamento(agendamentoId);
            });
        });
    } catch (error) {
        console.error('Erro ao buscar agendamentos:', error);
        const tbody = tabelaAgendamentos.querySelector('tbody') || tabelaAgendamentos.appendChild(document.createElement('tbody'));
        tbody.innerHTML = '<tr><td colspan="5">Erro ao carregar agendamentos</td></tr>';
    }
}

// Função para preencher campos (exposta globalmente)
const originalPreencherCampos = function (usuario) {
    console.log('Iniciando preenchimento de campos com:', usuario);
    if (!usuario) {
        console.error('Usuário inválido passado para preencherCampos');
        return;
    }

    const campos = {
        nome: { element: document.getElementById('nome'), key: 'nome' },
        dataNascimento: { element: document.getElementById('dataNascimento'), key: 'dataNascimento' },
        sexo: { element: document.getElementById('sexo'), key: 'sexo' },
        cpf: { element: document.getElementById('cpf'), key: 'cpf' },
        sus: { element: document.getElementById('sus'), key: 'numeroSus' },
        telefone: { element: document.getElementById('telefone'), key: 'telefone', format: formatarTelefoneCelular },
        celular: { element: document.getElementById('celular'), key: 'telCelular', format: formatarTelefoneCelular },
        email: { element: document.getElementById('email'), key: 'email' },
        logradouro: { element: document.getElementById('logradouro'), key: 'logradouro' },
        numero: { element: document.getElementById('numero'), key: 'numero' },
        complemento: { element: document.getElementById('complemento'), key: 'complemento' },
        cep: { element: document.getElementById('cep'), key: 'cep' },
        bairro: { element: document.getElementById('bairro'), key: 'bairro' },
        cidade: { element: document.getElementById('cidade'), key: 'cidade' },
        uf: { element: document.getElementById('uf'), key: 'uf' },
    };

    let atualizacoes = [];
    for (const [campo, { element, key, format }] of Object.entries(campos)) {
        if (!element) {
            console.warn(`Elemento #${campo} não encontrado no DOM. Ignorando.`);
            continue;
        }
        const valor = usuario[key];
        if (valor === undefined) {
            console.warn(`Propriedade ${key} não encontrada no usuário:`, usuario);
            element.value = '';
            atualizacoes.push(`#${campo}: [não encontrado]`);
            continue;
        }
        const valorFormatado = format ? format(valor) : (valor || '');
        element.value = valorFormatado;
        atualizacoes.push(`#${campo}: ${valorFormatado}`);
    }

    console.log('Campos atualizados:', atualizacoes);
    window.currentUsuario = usuario;
    console.log('Campos preenchidos com sucesso. window.currentUsuario atualizado:', window.currentUsuario);
};

// Sobrescreve preencherCampos condicionalmente para gerenciar-agendamentos.html
window.preencherCampos = function (usuario) {
    const isGerenciarAgendamentos = window.location.pathname.includes('gerenciar-agendamentos');
    console.log(`preencherCampos chamado. isGerenciarAgendamentos: ${isGerenciarAgendamentos}`);

    if (isGerenciarAgendamentos) {
        console.log('Executando preencherCampos para gerenciar-agendamentos.html');
        const campos = {
            nome: { element: document.getElementById('nome'), key: 'nome' },
            cpf: { element: document.getElementById('cpf'), key: 'cpf' },
            sus: { element: document.getElementById('sus'), key: 'numeroSus' },
        };

        let atualizacoes = [];
        for (const [campo, { element, key }] of Object.entries(campos)) {
            if (!element) {
                console.warn(`Elemento #${campo} não encontrado no DOM. Ignorando.`);
                continue;
            }
            const valor = usuario[key];
            if (valor === undefined) {
                console.warn(`Propriedade ${key} não encontrada no usuário:`, usuario);
                element.value = '';
                atualizacoes.push(`#${campo}: [não encontrado]`);
                continue;
            }
            element.value = valor || '';
            atualizacoes.push(`#${campo}: ${valor}`);
        }

        console.log('Campos atualizados:', atualizacoes);
        window.currentUsuario = usuario;
        console.log('Campos preenchidos com sucesso. window.currentUsuario atualizado:', window.currentUsuario);

        if (window.currentUsuario && window.currentUsuario.nome) {
            buscarAgendamentosPorNome(window.currentUsuario.nome);
        }
    } else {
        originalPreencherCampos(usuario);
    }
};

function limparCampos() {
    const campos = [
        'nome', 'dataNascimento', 'sexo', 'cpf', 'sus', 'telefone', 'celular',
        'email', 'logradouro', 'numero', 'complemento', 'cep', 'bairro', 'cidade', 'uf'
    ];

    campos.forEach(id => {
        const elemento = document.getElementById(id);
        if (elemento) {
            elemento.value = '';
        } else {
            console.warn(`Elemento #${id} não encontrado no DOM ao limpar campos`);
        }
    });
    window.currentUsuario = null;
    console.log('Campos limpos. window.currentUsuario resetado.');
}

// Função para carregar o modal.html dinamicamente
async function carregarModal() {
    try {
        console.log('Carregando modal.html...');
        const response = await fetch('/components/modal.html', { timeout: 5000 });
        if (!response.ok) {
            throw new Error(`Erro ao carregar modal.html: ${response.statusText}`);
        }
        const modalHtml = await response.text();
        console.log('modal.html carregado com sucesso');

        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = modalHtml;
        document.body.appendChild(tempDiv.firstElementChild);
        console.log('Modal adicionado ao DOM');
        initModal();
    } catch (error) {
        console.error('Erro ao carregar o modal:', error);
        alert('Erro ao carregar o modal. Verifique o console para mais detalhes.');
    }
}

// Funções de busca e eventos
async function buscarPorNome() {
    console.log('Função buscarPorNome chamada');
    const nome = document.getElementById('nome')?.value.trim();
    if (!nome) {
        console.log('Nome vazio, exibindo alerta');
        alert('Por favor, insira um nome para busca.');
        return;
    }

    console.log(`Preparando requisição para: /usuarios/nome?nome=${encodeURIComponent(nome)}`);
    try {
        console.log('Chamando api.get...');
        const data = await api.get(`/usuarios/nome?nome=${encodeURIComponent(nome)}`, { timeout: 5000 });
        console.log('Resposta recebida:', data);
        if (Object.keys(data).length === 0 || (Array.isArray(data) && data.length === 0)) {
            throw new Error('Nenhum usuário encontrado para o nome informado.');
        }

        if (Array.isArray(data) && data.length === 1) {
            limparResultadosNome();
            window.preencherCampos(data[0]);
        } else if (Array.isArray(data) && data.length > 1) {
            exibirResultadosNome(data);
        } else {
            limparResultadosNome();
            window.preencherCampos(data);
        }
    } catch (error) {
        console.error('Erro ao buscar por nome:', error);
        alert(`Erro ao buscar por nome: ${error.message}`);
        limparCampos();
        limparResultadosNome();
    }
}

function exibirResultadosNome(usuarios) {
    const resultadosDiv = document.getElementById('resultados-nome');
    if (!resultadosDiv) {
        console.error('Elemento #resultados-nome não encontrado no DOM');
        return;
    }
    resultadosDiv.innerHTML = '';
    resultadosDiv.style.display = 'block';

    usuarios.forEach((usuario, index) => {
        const item = document.createElement('div');
        item.style.padding = '5px';
        item.style.cursor = 'pointer';
        item.style.borderBottom = '1px solid #eee';
        item.textContent = `${usuario.nome} (CPF: ${usuario.cpf})`;
        item.addEventListener('click', () => {
            window.preencherCampos(usuario);
            resultadosDiv.style.display = 'none';
        });
        resultadosDiv.appendChild(item);
    });
}

function limparResultadosNome() {
    const resultadosDiv = document.getElementById('resultados-nome');
    if (resultadosDiv) {
        resultadosDiv.innerHTML = '';
        resultadosDiv.style.display = 'none';
    }
}

async function buscarPorCpf() {
    console.log('Função buscarPorCpf chamada');
    const cpfInput = document.getElementById('cpf');
    if (!cpfInput) {
        console.error('Elemento #cpf não encontrado no DOM');
        return;
    }
    const cpf = cpfInput.value;
    const cpfRegex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
    if (!cpf || !cpfRegex.test(cpf)) {
        alert('Por favor, insira um CPF válido no formato XXX.XXX.XXX-XX.');
        return;
    }

    try {
        const data = await api.get(`/usuarios/cpf/${encodeURIComponent(cpf)}`, { timeout: 5000 });
        window.preencherCampos(data);
    } catch (error) {
        alert(error.message);
        limparCampos();
    }
}

async function buscarPorSus() {
    console.log('Função buscarPorSus chamada');
    const sus = document.getElementById('sus');
    if (!sus) {
        console.error('Elemento #sus não encontrado no DOM');
        return;
    }
    const susValor = sus.value;
    if (!susValor) {
        alert('Por favor, insira um número SUS para busca.');
        return;
    }

    try {
        const data = await api.get(`/usuarios/sus/${encodeURIComponent(susValor)}`, { timeout: 5000 });
        window.preencherCampos(data);
    } catch (error) {
        alert(error.message);
        limparCampos();
    }
}

// Função para carregar especialidades
async function carregarEspecialidades() {
    const especialidadeSelect = document.getElementById('especialidade');
    if (!especialidadeSelect) {
        console.warn('Elemento #especialidade não encontrado no DOM');
        return;
    }

    try {
        const especialidades = await api.get('/especialidades', { timeout: 5000 });
        console.log('Especialidades recebidas:', especialidades);

        especialidadeSelect.innerHTML = '<option value="">Especialidade</option>';
        especialidades.forEach(especialidade => {
            const option = document.createElement('option');
            option.value = especialidade.id;
            option.textContent = especialidade.nome;
            especialidadeSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Erro ao carregar especialidades:', error);
        alert('Erro ao carregar especialidades. Verifique o console para mais detalhes.');
    }
}

// Função para carregar profissionais por especialidade
async function carregarProfissionais(especialidadeId) {
    const profissionalSelect = document.getElementById('profissional');
    if (!profissionalSelect) {
        console.warn('Elemento #profissional não encontrado no DOM');
        return;
    }

    profissionalSelect.innerHTML = '<option value="">Profissional (Opcional)</option>';
    if (!especialidadeId) return;

    try {
        const profissionais = await api.get('/profissionais', { timeout: 5000 });
        const profissionaisFiltrados = profissionais.filter(p => p.especialidade.id === parseInt(especialidadeId) && p.ativo);
        profissionaisFiltrados.forEach(profissional => {
            const option = document.createElement('option');
            option.value = profissional.id;
            option.textContent = profissional.nome;
            profissionalSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Erro ao carregar profissionais:', error);
    }
}

// Função para buscar dias disponíveis
async function buscarDiasDisponiveis(especialidadeId, startDate) {
    try {
        const hoje = new Date(startDate || new Date());
        hoje.setHours(0, 0, 0, 0); // Zerar hora pra comparação correta
        const diasFuturos = [];
        const maxDiasIniciais = 5; // 5 dias úteis pra evitar sobrecarga
        let diasContados = 0;
        let offset = 1; // Começa em D+1

        // Gera lista de dias úteis futuros
        while (diasContados < maxDiasIniciais) {
            const proximaData = new Date(hoje);
            proximaData.setDate(hoje.getDate() + offset);
            const diaSemana = proximaData.getDay();

            // Pula dias não úteis (sábado=6, domingo=0) e dias passados
            if (proximaData <= hoje || [0, 6].includes(diaSemana)) {
                offset++;
                continue;
            }

            const ano = proximaData.getFullYear();
            const mes = (proximaData.getMonth() + 1).toString().padStart(2, '0');
            const dia = proximaData.getDate().toString().padStart(2, '0');
            const dataStr = `${ano}-${mes}-${dia}`;
            diasFuturos.push({ date: proximaData, dataStr });
            diasContados++;
            offset++;
        }

        console.log(`Dias úteis futuros para verificação (a partir de ${hoje.toISOString().split('T')[0]}):`, diasFuturos.map(d => d.dataStr));

        // Paraleliza chamadas ao backend com timeout
        const resultados = await Promise.all(
            diasFuturos.map(async ({ date, dataStr }) => {
                try {
                    console.log(`Verificando disponibilidade para ${dataStr}`);
                    const horarios = await api.get(`/disponibilidade?especialidadeId=${especialidadeId}&data=${dataStr}`, { timeout: 3000 });
                    return { date, dataStr, hasHorarios: Array.isArray(horarios) && horarios.length > 0 };
                } catch (error) {
                    console.error(`Erro ao verificar ${dataStr}:`, error);
                    return { date, dataStr, hasHorarios: false };
                }
            })
        );

        const diasDisponiveis = resultados
            .filter(result => result.hasHorarios)
            .map(result => result.date);

        console.log(`Dias com horários disponíveis:`, diasDisponiveis.map(d => d.toISOString().split('T')[0]));
        return diasDisponiveis;
    } catch (error) {
        console.error('Erro ao buscar dias disponíveis:', error);
        return [];
    }
}

// Função para restringir o calendário com base na disponibilidade
async function restringirCalendario(especialidadeId) {
    const dataAgendamentoInput = document.getElementById('dataAgendamento');
    const horarioSelect = document.getElementById('horario');
    if (!dataAgendamentoInput || !horarioSelect) {
        console.warn('Elementos #dataAgendamento ou #horario não encontrados no DOM');
        return;
    }

    dataAgendamentoInput.value = '';
    horarioSelect.innerHTML = '<option value="">Horário</option>';

    if (!especialidadeId) {
        dataAgendamentoInput.disabled = true;
        horarioSelect.disabled = true;
        return;
    }

    dataAgendamentoInput.disabled = false;

    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0); // Zerar hora pra comparação correta
    const dMaisUm = new Date(hoje);
    dMaisUm.setDate(hoje.getDate() + 1);
    console.log(`Hoje: ${hoje.toISOString().split('T')[0]}, D+1: ${dMaisUm.toISOString().split('T')[0]}`);

    let diasDisponiveis = [];
    try {
        diasDisponiveis = await buscarDiasDisponiveis(especialidadeId, hoje);
    } catch (error) {
        console.error('Erro ao carregar dias disponíveis:', error);
        alert('Erro ao carregar dias disponíveis. Tente novamente.');
        dataAgendamentoInput.disabled = true;
        horarioSelect.disabled = true;
        return;
    }

    if (diasDisponiveis.length === 0) {
        alert('Nenhum dia útil com horários disponíveis nos próximos 5 dias.');
        dataAgendamentoInput.disabled = true;
        horarioSelect.disabled = true;
        return;
    }

    const flatpickrInstance = flatpickr(dataAgendamentoInput, {
        enable: diasDisponiveis,
        minDate: dMaisUm,
        dateFormat: 'd/m/Y',
        locale: "pt",
        onDayCreate: (dObj, dStr, fp, dayElem) => {
            const date = dayElem.dateObj;
            const isEnabled = diasDisponiveis.some(d => 
                d.getDate() === date.getDate() &&
                d.getMonth() === date.getMonth() &&
                d.getFullYear() === date.getFullYear()
            );
            if (isEnabled) {
                dayElem.classList.add('disponivel');
            }
        },
        onChange: async (selectedDates, dateStr) => {
            const [dia, mes, ano] = dateStr.split('/');
            const dataFormatada = `${ano}-${mes}-${dia}`;
            await carregarHorariosDisponiveis(especialidadeId, dataFormatada, horarioSelect);
        },
        onMonthChange: async (selectedDates, dateStr, instance) => {
            const currentMonth = instance.currentMonth + 1;
            const currentYear = instance.currentYear;
            const novoHoje = new Date(currentYear, currentMonth - 1, 1);
            novoHoje.setHours(0, 0, 0, 0); // Normaliza hora
            console.log(`Mudou para mês: ${currentMonth}/${currentYear}, novoHoje: ${novoHoje.toISOString().split('T')[0]}`);
            try {
                const novosDias = await buscarDiasDisponiveis(especialidadeId, novoHoje);
                console.log('Novos dias disponíveis:', novosDias.map(d => d.toISOString().split('T')[0]));
                const diasAtuais = instance.config.enable || [];
                const diasUnicos = [...new Set([...diasAtuais, ...novosDias].map(d => d.toISOString().split('T')[0]))]
                    .map(d => new Date(d + 'T00:00:00-03:00')); // Ajusta timezone
                console.log('Dias únicos após merge:', diasUnicos.map(d => d.toISOString().split('T')[0]));
                instance.set('enable', diasUnicos);
                instance.redraw();
            } catch (error) {
                console.error('Erro ao atualizar dias no onMonthChange:', error);
                alert('Erro ao atualizar dias disponíveis.');
            }
        }
    });
}

// Função para carregar horários disponíveis
async function carregarHorariosDisponiveis(especialidadeId, data, selectElement) {
    if (!selectElement) {
        console.warn('Select não encontrado para carregarHorariosDisponiveis');
        return;
    }

    selectElement.innerHTML = '<option value="">Carregando...</option>';

    if (!especialidadeId || !data) {
        selectElement.innerHTML = '<option value="">Selecione uma data</option>';
        selectElement.disabled = true;
        return;
    }

    selectElement.disabled = false;

    try {
        console.log(`Buscando horários: especialidadeId=${especialidadeId}, data=${data}`);
        const horariosDisponiveis = await api.get(`/disponibilidade?especialidadeId=${especialidadeId}&data=${data}`, { timeout: 3000 });
        console.log('Horários disponíveis recebidos:', horariosDisponiveis);

        selectElement.innerHTML = '<option value="">Selecione um horário</option>';
        if (Array.isArray(horariosDisponiveis) && horariosDisponiveis.length > 0) {
            horariosDisponiveis.forEach(horario => {
                const hora = String(horario.hour).padStart(2, '0');
                const minuto = String(horario.minute).padStart(2, '0');
                const horarioStr = `${hora}:${minuto}`;
                const option = new Option(horarioStr, horarioStr);
                selectElement.appendChild(option);
            });
        } else {
            console.warn('Nenhum horário disponível retornado pelo backend.');
            selectElement.innerHTML = '<option value="">Nenhum horário disponível. Escolha outra data.</option>';
            selectElement.disabled = true;
        }
    } catch (error) {
        console.error('Erro ao carregar horários disponíveis:', error);
        selectElement.innerHTML = '<option value="">Erro ao carregar horários</option>';
        selectElement.disabled = true;
    }
}

// Função para carregar agendamentos do dia atual
async function carregarAgendamentosHoje() {
    const tabelaAgendamentos = document.getElementById('tabela-agendamentos');
    if (!tabelaAgendamentos) {
        console.warn('Elemento #tabela-agendamentos não encontrado no DOM');
        return;
    }

    try {
        const hoje = new Date().toISOString().split('T')[0];
        const agendamentos = await api.get(`/agendamentos?data=${hoje}`, { timeout: 5000 });
        console.log('Agendamentos recebidos do backend:', agendamentos);

        const agendamentosHoje = agendamentos.filter(a => a.data === hoje);
        agendamentosHoje.sort((a, b) => {
            const [horaA, minA] = a.horario.split(':').map(Number);
            const [horaB, minB] = b.horario.split(':').map(Number);
            return horaA !== horaB ? horaA - horaB : minA - minB;
        });

        console.log('Agendamentos de hoje (filtrados e ordenados):', agendamentosHoje);

        tabelaAgendamentos.innerHTML = '';

        if (agendamentosHoje.length === 0) {
            tabelaAgendamentos.innerHTML = '<tr><td colspan="3">Nenhum agendamento para hoje</td></tr>';
        } else {
            agendamentosHoje.forEach(agendamento => {
                const [hora, minuto] = agendamento.horario.split(':');
                const horarioFormatado = `${hora}:${minuto}h`;
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${agendamento.usuario.nome}</td>
                    <td>${horarioFormatado}</td>
                    <td>${agendamento.especialidade.nome}</td>
                `;
                tabelaAgendamentos.appendChild(tr);
            });
        }
    } catch (error) {
        console.error('Erro ao carregar agendamentos de hoje:', error);
        tabelaAgendamentos.innerHTML = '<tr><td colspan="3">Erro ao carregar agendamentos</td></tr>';
    }
}

// Função para agendar consulta
async function agendarConsulta() {
    const especialidadeId = document.getElementById('especialidade')?.value;
    const dataStr = document.getElementById('dataAgendamento')?.value;
    const horario = document.getElementById('horario')?.value;
    const procedimento = document.getElementById('procedimento')?.value || null;
    const profissionalId = document.getElementById('profissional')?.value || null;

    if (!window.currentUsuario) {
        alert('Por favor, selecione um usuário antes de agendar.');
        return;
    }

    if (!especialidadeId || !dataStr || !horario) {
        alert('Por favor, preencha os campos obrigatórios: especialidade, data e horário.');
        return;
    }

    const [dia, mes, ano] = dataStr.split('/');
    const data = `${ano}-${mes}-${dia}`;
    const [hour, minute] = horario.split(':').map(Number);
    const horarioFormatado = `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:00`;

    const agora = new Date();
    const dataAgendamento = new Date(`${data}T${horarioFormatado}-03:00`);
    if (dataAgendamento <= agora) {
        alert('Não é possível agendar no passado. Escolha um horário futuro.');
        return;
    }

    const agendamento = {
        usuarioId: window.currentUsuario.id,
        especialidadeId: parseInt(especialidadeId),
        data: data,
        horario: horarioFormatado,
        procedimento: procedimento,
        profissionalId: profissionalId ? parseInt(profissionalId) : null
    };

    console.log('Objeto agendamento a ser enviado:', agendamento);

    try {
        const response = await api.post('/agendamentos', agendamento, { timeout: 5000 });
        console.log('Resposta do backend:', response);

        if (response && response.error) {
            throw new Error(response.error);
        }

        alert('Agendamento realizado com sucesso!');

        document.getElementById('especialidade').value = '';
        document.getElementById('dataAgendamento').value = '';
        document.getElementById('horario').innerHTML = '<option value="">Horário</option>';
        document.getElementById('horario').disabled = true;
        document.getElementById('dataAgendamento').disabled = true;
        document.getElementById('procedimento').value = '';
        document.getElementById('profissional').innerHTML = '<option value="">Profissional (Opcional)</option>';

        await carregarAgendamentosHoje();
    } catch (error) {
        console.error('Erro ao realizar agendamento:', error);
        let errorMessage = 'Erro ao realizar agendamento. Verifique o console para mais detalhes.';
        if (error.message.includes('400')) {
            errorMessage = 'Erro: Dados inválidos. Verifique os campos e tente novamente.';
        } else if (error.message.includes('409')) {
            errorMessage = 'Erro: Este horário já está ocupado. Escolha outro horário.';
        } else if (error.message.includes('500')) {
            errorMessage = 'Erro no servidor. Tente novamente mais tarde.';
        }
        alert(errorMessage);
    }
}

// Função para abrir o modal de edição
async function abrirModalEditarAgendamento(agendamentoId) {
    const modal = document.getElementById('modal-editar-agendamento');
    if (!modal) {
        console.error('Modal #modal-editar-agendamento não encontrado.');
        alert('Erro: Modal não encontrado.');
        return;
    }

    const especialidadeSpan = document.getElementById('modal-especialidade');
    const dataInput = document.getElementById('modal-data');
    const horarioSelect = document.getElementById('modal-horario');
    if (!especialidadeSpan || !dataInput || !horarioSelect) {
        console.error('Elementos do modal não encontrados:', { especialidadeSpan, dataInput, horarioSelect });
        alert('Erro: Elementos do modal não encontrados.');
        return;
    }

    let agendamento;
    try {
        console.log(`Carregando agendamento com ID: ${agendamentoId}`);
        agendamento = await api.get(`/agendamentos/${agendamentoId}`, { timeout: 5000 });
        console.log('Agendamento carregado:', agendamento);
    } catch (error) {
        console.error('Erro ao carregar agendamento:', error);
        alert('Erro ao carregar dados do agendamento.');
        return;
    }

    especialidadeSpan.textContent = agendamento.especialidade?.nome || 'Sem especialidade';
    if (agendamento.data) {
        const [ano, mes, dia] = agendamento.data.split('-');
        dataInput.value = `${dia}/${mes}/${ano}`;
    } else {
        dataInput.value = '';
    }

    // Inicializa Flatpickr com dias disponíveis
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);
    const dMaisUm = new Date(hoje);
    dMaisUm.setDate(hoje.getDate() + 1);
    let diasDisponiveis = [];
    try {
        diasDisponiveis = await buscarDiasDisponiveis(agendamento.especialidade?.id, hoje);
    } catch (error) {
        console.error('Erro ao carregar dias disponíveis no modal:', error);
        alert('Erro ao carregar dias disponíveis.');
        return;
    }

    const flatpickrInstance = flatpickr(dataInput, {
        enable: diasDisponiveis,
        minDate: dMaisUm,
        dateFormat: 'd/m/Y',
        locale: "pt",
        onDayCreate: (dObj, dStr, fp, dayElem) => {
            const date = dayElem.dateObj;
            const isEnabled = diasDisponiveis.some(d => 
                d.getDate() === date.getDate() &&
                d.getMonth() === date.getMonth() &&
                d.getFullYear() === date.getFullYear()
            );
            if (isEnabled) {
                dayElem.classList.add('disponivel');
            }
        },
        onChange: async (selectedDates, dateStr) => {
            if (!dateStr) return;
            const [dia, mes, ano] = dateStr.split('/');
            const dataBackend = `${ano}-${mes}-${dia}`;
            await carregarHorariosDisponiveis(agendamento.especialidade?.id, dataBackend, horarioSelect);
        },
        onMonthChange: async (selectedDates, dateStr, instance) => {
            const currentMonth = instance.currentMonth + 1;
            const currentYear = instance.currentYear;
            const novoHoje = new Date(currentYear, currentMonth - 1, 1);
            novoHoje.setHours(0, 0, 0, 0); // Normaliza hora
            console.log(`Mudou para mês: ${currentMonth}/${currentYear}, novoHoje: ${novoHoje.toISOString().split('T')[0]}`);
            try {
                const novosDias = await buscarDiasDisponiveis(agendamento.especialidade?.id, novoHoje);
                console.log('Novos dias disponíveis no modal:', novosDias.map(d => d.toISOString().split('T')[0]));
                const diasAtuais = instance.config.enable || [];
                const diasUnicos = [...new Set([...diasAtuais, ...novosDias].map(d => d.toISOString().split('T')[0]))]
                    .map(d => new Date(d + 'T00:00:00-03:00')); // Ajusta timezone
                console.log('Dias únicos no modal:', diasUnicos.map(d => d.toISOString().split('T')[0]));
                instance.set('enable', diasUnicos);
                instance.redraw();
            } catch (error) {
                console.error('Erro ao atualizar dias no modal:', error);
                alert('Erro ao atualizar dias disponíveis.');
            }
        }
    });

    if (agendamento.horario) {
        const [hora, minuto] = agendamento.horario.split(':');
        const horarioInicial = `${hora}:${minuto}`;
        try {
            await carregarHorariosDisponiveis(agendamento.especialidade?.id, agendamento.data, horarioSelect);
            horarioSelect.value = horarioInicial;
        } catch (error) {
            console.error('Erro ao carregar horários iniciais no modal:', error);
            horarioSelect.innerHTML = '<option value="">Erro ao carregar horários</option>';
        }
    } else {
        horarioSelect.innerHTML = '<option value="">Selecione um horário</option>';
    }

    modal.classList.remove('hidden');

    const closeButton = modal.querySelector('.close');
    closeButton.addEventListener('click', () => {
        modal.classList.add('hidden');
        flatpickrInstance.destroy();
    });

    const btnReagendar = document.getElementById('btn-reagendar');
    btnReagendar.onclick = async () => {
        const dataStr = dataInput.value;
        const novoHorario = horarioSelect.value;
        if (!dataStr || !novoHorario) {
            alert('Selecione uma data e horário válidos.');
            return;
        }

        const [dia, mes, ano] = dataStr.split('/');
        const novaData = `${ano}-${mes}-${dia}`;
        const horarioFormatado = `${novoHorario}:00`;

        const agora = new Date();
        const dataAgendamento = new Date(`${novaData}T${horarioFormatado}-03:00`);
        if (dataAgendamento <= agora) {
            alert('Não agende no passado. Escolha um horário futuro.');
            return;
        }

        const dadosReagendamento = {
            data: novaData,
            horario: horarioFormatado
        };
        console.log('Dados enviados para reagendamento:', dadosReagendamento);

        try {
            await api.put(`/agendamentos/${agendamentoId}`, dadosReagendamento, { timeout: 5000 });
            alert('Reagendado com sucesso!');
            modal.classList.add('hidden');
            flatpickrInstance.destroy();
            const nomeInput = document.getElementById('nome');
            if (nomeInput && nomeInput.value) {
                await buscarAgendamentosPorNome(nomeInput.value);
            }
        } catch (error) {
            console.error('Erro ao reagendar:', error);
            let errorMessage = 'Erro ao reagendar. Verifique o console para mais detalhes.';
            if (error.message.includes('400')) {
                errorMessage = 'Erro: Dados inválidos. Verifique os campos e tente novamente.';
            } else if (error.message.includes('409')) {
                errorMessage = 'Erro: Este horário já está ocupado. Escolha outro horário.';
            } else if (error.message.includes('500')) {
                errorMessage = 'Erro no servidor. Tente novamente mais tarde.';
            }
            alert(errorMessage);
        }
    };

    const btnDesmarcar = document.getElementById('btn-desmarcar');
    btnDesmarcar.onclick = async () => {
        if (confirm('Deseja desmarcar este agendamento?')) {
            try {
                console.log(`Deletando agendamento com ID: ${agendamentoId}`);
                await api.delete(`/agendamentos/${agendamentoId}`, { timeout: 5000 });
                alert('Desmarcado com sucesso!');
                modal.classList.add('hidden');
                flatpickrInstance.destroy();
                const nomeInput = document.getElementById('nome');
                if (nomeInput && nomeInput.value) {
                    await buscarAgendamentosPorNome(nomeInput.value);
                }
            } catch (error) {
                console.error('Erro ao desmarcar:', error);
                let errorMessage = 'Erro ao desmarcar. Verifique o console para mais detalhes.';
                if (error.message.includes('404')) {
                    errorMessage = 'Erro: Agendamento não encontrado. Pode já ter sido deletado.';
                } else if (error.message.includes('500')) {
                    errorMessage = 'Erro no servidor. Tente novamente mais tarde.';
                }
                alert(errorMessage);
            }
        }
    };
}

// Envolve o código em DOMContentLoaded para garantir que o DOM esteja carregado
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM carregado. Caminho atual:', window.location.pathname);

    // Adiciona máscara ao campo CPF
    const cpfInput = document.getElementById('cpf');
    if (cpfInput) {
        cpfInput.addEventListener('input', (e) => aplicarMascaraCPF(e.target));
    }

    // Adiciona máscaras aos campos telefone e celular (apenas para index.html)
    const telefoneInput = document.getElementById('telefone');
    if (telefoneInput) {
        telefoneInput.addEventListener('input', (e) => aplicarMascaraTelefoneCelular(e.target));
    }

    const celularInput = document.getElementById('celular');
    if (celularInput) {
        celularInput.addEventListener('input', (e) => aplicarMascaraTelefoneCelular(e.target));
    }

    // Adiciona eventos aos botões de busca (comuns às duas páginas)
    const btnBuscaNome = document.getElementById('btn-busca-nome');
    if (btnBuscaNome) {
        btnBuscaNome.addEventListener('click', buscarPorNome);
        console.log('Evento adicionado ao #btn-busca-nome');
    }

    const btnBuscaCpf = document.getElementById('btn-busca-cpf');
    if (btnBuscaCpf) {
        btnBuscaCpf.addEventListener('click', buscarPorCpf);
        console.log('Evento adicionado ao #btn-busca-cpf');
    }

    const btnBuscaSus = document.getElementById('btn-busca-sus');
    if (btnBuscaSus) {
        btnBuscaSus.addEventListener('click', buscarPorSus);
        console.log('Evento adicionado ao #btn-busca-sus');
    }

    // Funcionalidades específicas de index.html
    const isIndexPage = window.location.pathname.includes('index') || window.location.pathname === '/';
    console.log('É index.html?', isIndexPage);
    if (isIndexPage) {
        carregarModal();
        carregarEspecialidades();
        carregarAgendamentosHoje();

        const abrirModalBtn = document.getElementById('abrir-modal-btn');
        if (abrirModalBtn) {
            abrirModalBtn.addEventListener('click', () => {
                console.log('Botão Editar / Novo clicado');
                if (typeof window.abrirModalUsuario === 'function') {
                    window.abrirModalUsuario(window.currentUsuario);
                } else {
                    console.error('window.abrirModalUsuario não é uma função.');
                }
            });
        }

        const especialidadeSelect = document.getElementById('especialidade');
        if (especialidadeSelect) {
            especialidadeSelect.addEventListener('change', (e) => {
                const especialidadeId = e.target.value;
                restringirCalendario(especialidadeId);
                carregarProfissionais(especialidadeId);
            });
        }

        const btnAgendar = document.getElementById('btn-agendar');
        if (btnAgendar) {
            btnAgendar.addEventListener('click', agendarConsulta);
        }
    }
});