fetch("/ticketsByConcert")
.then(response => response.json())
.then(data => {
    const ctx = document.getElementById("graphicBar").getContext("2d");

    function getRandomColor() {
        const letters = '0123456789ABCDEF';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    const colors = data.labels.map(() => getRandomColor()); 

    new Chart(ctx, {
        type: "bar",
        data: {
            labels: data.labels,
            datasets: [{
                label: "Tickets Sold",
                data: data.data,
                backgroundColor: colors,
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    ticks: {
                        color: 'white'
                    }
                },
                y: { 
                    beginAtZero: true,
                    ticks: {
                        color: 'white'
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
})
.catch(error => console.error("Error fetching chart data:", error));