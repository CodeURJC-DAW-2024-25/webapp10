let offset = 4; // Inicialmente mostramos 4 conciertos

        $('#load-more-btn').on('click', function() {
            // Mostrar el spinner
            $('#spinner').show();

            $.ajax({
                url: '/loadMoreConcerts',
                method: 'GET',
                data: { offset: offset },
                success: function(data) {
                    if (data.length > 0) {
                        // Añadir los nuevos conciertos al final de la lista
                        data.forEach(function(concert, index) {
                            // Verificar si hay imagen o usar una por defecto
                            let imageSrc = concert.concertImage ? concert.imagefile : "/images/Concerts/noImage.jpg";
                            let imageAlt = concert.concertImage ? concert.name : "No image available";

                            $('#concert-list').append(`
                                <article class="concert-card">
                                    <a href="concert/${index}" class="link">
                                        <img src="${imageSrc}" alt="${imageAlt}">
                                    </a>
                                </article>
                                    
                            `);
                        });

                        
                        // Actualizamos el offset
                        offset += 4;
                    } else {
                        $('#load-more-btn').hide(); // Ocultar el botón si no hay más conciertos
                    }
                    // Ocultar el spinner después de cargar los conciertos
                    $('#spinner').hide();
                }
            });
        });
