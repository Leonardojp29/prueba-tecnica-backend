@extends('layouts.app')

@section('title', 'Listado de artículos')

@section('content')
    <h1 class="mb-4">Artículos publicados</h1>

    <div class="row">
        @foreach ($articles as $article)
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">{{ $article['title'] }}</h5>
                        <p class="card-text text-muted">{{ Str::limit($article['body'], 100) }}</p>

                        <p class="card-text">
                            <strong>Estado:</strong>
                            @if ($article['status'] === 'PUBLISHED')
                                <span class="badge bg-success">Publicado</span>
                            @else
                                <span class="badge bg-secondary">No publicado</span>
                            @endif
                        </p>

                        <p class="card-text">
                            <strong>Categoría:</strong> {{ $article['category']['name'] ?? 'Sin categoría' }}
                        </p>

                        <p class="card-text">
                            <strong>Autores:</strong>
                            @foreach ($article['authors'] as $author)
                                {{ $author['name'] }}@if (!$loop->last), @endif
                            @endforeach
                        </p>

                        <a href="{{ route('articles.show', $article['slug']) }}" class="btn btn-outline-primary btn-sm">Ver más</a>
                        <a href="{{ route('articles.edit', $article['id']) }}" class="btn btn-outline-warning btn-sm">Editar</a>

                        <form action="{{ route('articles.destroy', $article['id']) }}" method="POST" class="d-inline" onsubmit="return confirm('¿Estás seguro de que deseas eliminar este artículo?')">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                        </form>

                    </div>
                </div>
            </div>
        @endforeach
    </div>
@endsection
