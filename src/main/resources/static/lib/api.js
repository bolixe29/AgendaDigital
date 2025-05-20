const BASE_URL = "";

const api = {
  async get(endpoint) {
    console.log(`api.get chamado com endpoint: ${endpoint}`);
    const response = await fetch(`${BASE_URL}${endpoint}`);
    console.log(`Resposta recebida com status: ${response.status}`);
    if (!response.ok) throw new Error(`Erro GET: ${response.status}`);
    return await response.json();
  },

  async post(endpoint, data) {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error(`Erro POST: ${response.status}`);
    return await response.json();
  },

  async put(endpoint, data) {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error(`Erro PUT: ${response.status}`);
    return await response.json();
  },

  async delete(endpoint) {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      method: "DELETE"
    });
    if (!response.ok) throw new Error(`Erro DELETE: ${response.status}`);
    return await response.json();
  }
};

export { api };