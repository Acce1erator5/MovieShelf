package uz.ttpu.movieshelf.data.repository

import uz.ttpu.movieshelf.data.local.MovieLocalDataSource
import uz.ttpu.movieshelf.data.mapper.toDomain
import uz.ttpu.movieshelf.data.remote.MovieRemoteDataSource
import uz.ttpu.movieshelf.domain.model.MoviesResult
import java.io.IOException

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) {
    suspend fun getMovies(): MoviesResult {
        return try {
            val remoteMovies = remoteDataSource.fetchMovies()
            localDataSource.saveMovies(remoteMovies)

            val favoriteIds = localDataSource.getFavoriteIds()
            val domainMovies = remoteMovies.map { dto ->
                dto.toDomain(isFavorite = favoriteIds.contains(dto.id))
            }
            MoviesResult(movies = domainMovies, isFromCache = false)
        } catch (e: IOException) {
            val cachedMovies = localDataSource.getCachedMovies()
                ?: throw IOException("No connection and no cache", e)

            val favoriteIds = localDataSource.getFavoriteIds()
            val domainMovies = cachedMovies.map { dto ->
                dto.toDomain(isFavorite = favoriteIds.contains(dto.id))
            }
            MoviesResult(movies = domainMovies, isFromCache = true)
        }
    }

    suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        localDataSource.setFavorite(movieId, isFavorite)
    }
}
