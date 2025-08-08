@extends('layouts.app')

@section('title', 'Detalle del artículo')

@section('content')
<div class="container">
  <h2 class="mb-3">{{ $article['title'] }}</h2>

  <p><strong>Categoría:</strong> {{ $article['category']['name'] ?? 'Sin categoría' }}</p>
  <p><strong>Estado:</strong> {{ $article['status'] }}</p>
  <p><strong>Contenido:</strong></p>
  <p>{{ $article['body'] }}</p>

  <hr>

  <p><strong>Autores:</strong></p>
  <ul>
    @foreach ($article['authors'] as $author)
      <li>{{ $author['name'] }} ({{ $author['email'] }})</li>
    @endforeach
  </ul>

  <a href="{{ route('articles.index') }}" class="btn btn-secondary mt-3">Volver</a>
</div>
@endsection
