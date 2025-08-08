@extends('layouts.app')

@section('title', 'Crear nuevo artículo')

@section('content')
<div class="container">
  <h2 class="mb-4">Nuevo Artículo</h2>

  @if ($errors->any())
    <div class="alert alert-danger">
      <strong>Ups!</strong> Hay algunos errores:<br>
      <ul class="mb-0">
        @foreach ($errors->all() as $error)
          <li>{{ $error }}</li>
        @endforeach
      </ul>
    </div>
  @endif

  <form method="POST" action="{{ route('articles.store') }}">
    @csrf

    <div class="mb-3">
      <label for="title" class="form-label">Título</label>
      <input type="text" name="title" class="form-control" id="title" value="{{ old('title') }}" required>
    </div>

    <div class="mb-3">
      <label for="body" class="form-label">Contenido</label>
      <textarea name="body" class="form-control" id="body" rows="5" required>{{ old('body') }}</textarea>
    </div>

    <div class="mb-3">
      <label for="category_id" class="form-label">Categoría</label>
      <select name="category_id" class="form-select" id="category_id" required>
        <option value="">Seleccione una categoría</option>
        @foreach ($categories as $category)
            <option value="{{ $category['id'] }}" {{ old('category_id') == $category['id'] ? 'selected' : '' }}>
            {{ $category['name'] }}
            </option>
        @endforeach
        </select>
    </div>

    <div class="mb-3">
      <label for="authors" class="form-label">Autores</label>
        <select name="authors[]" id="authors" class="form-select" multiple required>
        @foreach ($authors as $author)
            <option value="{{ $author['id'] }}" {{ collect(old('authors'))->contains($author['id']) ? 'selected' : '' }}>
            {{ $author['name'] }}
            </option>
        @endforeach
        </select>
    </div>

    <div class="mb-3">
      <label for="status" class="form-label">Estado</label>
      <select name="status" class="form-select" id="status" required>
        <option value="UNPUBLISHED" {{ old('status') == 'UNPUBLISHED' ? 'selected' : '' }}>Borrador</option>
        <option value="PUBLISHED" {{ old('status') == 'PUBLISHED' ? 'selected' : '' }}>Publicado</option>
      </select>
    </div>

    <button type="submit" class="btn btn-success">Guardar</button>
    <a href="{{ route('articles.index') }}" class="btn btn-secondary ms-2">Cancelar</a>
  </form>
</div>
@endsection
