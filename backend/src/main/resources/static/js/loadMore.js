let page = 0;

async function loadMore() {
    page++;

    document.getElementById("spinner").style.display = "block";

    try{
        const response = await fetch(`/moreConcerts?page=${page}`);
        const data = await response.text();
    
    
    
        document.getElementById("allConcerts").innerHTML += data;
    
        if (data.includes("<!--true-->")) {
            document.getElementById('load-more-btn').style.display = 'none';
        }
    } finally{
        document.getElementById("spinner").style.display = "none";
    }


    

}