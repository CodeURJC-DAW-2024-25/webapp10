document.addEventListener("DOMContentLoaded", async () => {
    const graph = document.getElementById('graphicPie').getContext('2d');
    const id = document.getElementById("concertId").getAttribute("data-id");
    const response = await fetch(`/infoGraphic/${id}`); 
    const dataResponse = await response.json(); 
    graph.width=250;
    graph.height=250;
    new Chart(graph, {
        type: 'pie',
        data: {
            labels: dataResponse.labels,
            datasets: [{
                data: dataResponse.data,
                backgroundColor: dataResponse.backgroundColor
            }]
        },
        options: {
            responsive: false,
            maintainAspectRatio: false
        }
    });
});



