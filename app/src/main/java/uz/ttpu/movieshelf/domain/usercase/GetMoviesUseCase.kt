package uz.ttpu.movieshelf.domain.usecase

import uz.ttpu.movieshelf.data.repository.MovieRepositoryImpl
import uz.ttpu.movieshelf.domain.model.MoviesResult

class GetMoviesUseCase(private val repository: MovieRepositoryImpl) {
    // Оператор invoke позволяет вызывать юзкейс как функцию: getMoviesUseCase()
    suspend operator fun invoke(): MoviesResult {
        return repository.getMovies()
    }
}
