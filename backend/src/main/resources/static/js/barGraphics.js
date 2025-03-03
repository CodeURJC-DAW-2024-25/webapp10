fetch("/ticketsByConcert")
.then(response => response.json())
.then(data => {
    const ctx = document.getElementById("graphicBar").getContext("2d");

    // Generar un color aleatorio para cada concierto
    function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
    }

    const colors = data.labels.map(() => getRandomColor()); // Crear un color aleatorio para cada concierto

    new Chart(ctx, {
    type: "bar",
    data: {
        labels: data.labels,
        datasets: [{
        label: "Entradas Vendidas",
        data: data.data,
        backgroundColor: colors, // Asignar colores aleatorios a las barras
        }]
    },
    options: {
        responsive: true,
        scales: {
        y: { 
            beginAtZero: true 
        }
        }
    }
    });
})
.catch(error => console.error("Error al obtener los datos del gráfico:", error));