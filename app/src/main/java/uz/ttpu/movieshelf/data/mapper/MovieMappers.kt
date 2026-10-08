package uz.ttpu.movieshelf.data.mapper

import uz.ttpu.movieshelf.data.remote.MovieDto
import uz.ttpu.movieshelf.domain.model.Movie

fun MovieDto.toDomain(isFavorite: Boolean): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        year = this.releaseYear,
        rating = this.score,
        isFavorite = isFavorite
    )
}
