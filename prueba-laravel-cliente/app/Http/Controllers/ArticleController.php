<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\View\View;
use Illuminate\Http\RedirectResponse;

class ArticleController extends Controller
{   
    //Muestra la lista de artículos
    public function index(): View
    {
        $response = Http::get(env('API_URL') . '/articles');

        if ($response->successful()) {
            $articles = $response->json();
            return view('articles.index', ['articles' => $articles]);
        }

        abort(500, 'No se pudo obtener los artículos');
    }


    //Muestra el detalle de un artículo
    public function show(string $slug): View
    {
        $response = Http::get(env('API_URL') . "/articles/blog/{$slug}");

        if ($response->successful()) {
            $article = $response->json();
            return view('articles.show', ['article' => $article]);
        }

        abort(404, 'Artículo no encontrado');
    }


    //Muestra el formulario para crear un artículo
    public function create(): View
    {
        $categories = collect(Http::get(env('API_URL') . '/categories')->json());
        $authors = collect(Http::get(env('API_URL') . '/authors')->json());

        return view('articles.create', [
            'categories' => $categories,
            'authors' => $authors,
        ]);
    }


    //Guarda un artículo
    public function store(Request $request): RedirectResponse
    {
        $validated = $request->validate([
            'title' => 'required|string|max:255',
            'body' => 'required|string',
            'category_id' => 'required|integer',
            'authors' => 'required|array',
            'status' => 'required|in:UNPUBLISHED,PUBLISHED',
        ]);

        $validated['authors'] = array_map('intval', $validated['authors']);
        $validated['category_id'] = (int) $validated['category_id'];

        $response = Http::post(env('API_URL') . '/articles', $validated);

        if ($response->failed()) {
            dump('Respuesta fallida del backend:', $response->body());
            return back()->withInput()->withErrors(['message' => 'Error al crear el artículo']);
        }

        return redirect()->route('articles.index')->with('success', 'Artículo creado correctamente');
    }


    //Muestra el formulario para editar un artículo
    public function edit(int $id): View
    {
        $articleResponse = Http::get(env('API_URL') . "/articles/{$id}");
        $categoriesResponse = Http::get(env('API_URL') . '/categories');
        $authorsResponse = Http::get(env('API_URL') . '/authors');

        if ($articleResponse->failed() || $categoriesResponse->failed() || $authorsResponse->failed()) {
            abort(500, 'No se pudo cargar la información para editar el artículo');
        }

        $article = $articleResponse->json();
        $categories = $categoriesResponse->json();
        $authors = $authorsResponse->json();

        return view('articles.edit', [
            'article' => $article,
            'categories' => $categories,
            'authors' => $authors,
        ]);
    }

    //Actualiza unn artículo
    public function update(Request $request, int $id): RedirectResponse
    {
        $validated = $request->validate([
            'title' => 'required|string|max:255',
            'body' => 'required|string',
            'category_id' => 'required|integer',
            'authors' => 'required|array',
            'status' => 'required|in:PUBLISHED,UNPUBLISHED',
        ]);

        $validated['authors'] = array_map('intval', $validated['authors']);
        $validated['category_id'] = (int) $validated['category_id'];

        $response = Http::put(env('API_URL') . "/articles/{$id}", $validated);

        if ($response->failed()) {
            return back()->withInput()->withErrors(['message' => 'Error al actualizar el artículo']);
        }

        return redirect()->route('articles.index')->with('success', 'Artículo actualizado correctamente');
    }

    //Elimina un artículo
    public function destroy(int $id): RedirectResponse
    {
        $response = Http::delete(env('API_URL') . "/articles/{$id}");

        if ($response->failed()) {
            return back()->withErrors(['message' => 'No se pudo eliminar el artículo']);
        }

        return redirect()->route('articles.index')->with('success', 'Artículo eliminado correctamente');
    }


    




}
