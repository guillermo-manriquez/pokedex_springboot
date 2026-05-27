package com.pokemon.pokemon_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemon.pokemon_service.entity.Pokedex;
import com.pokemon.pokemon_service.entity.Pokemon;
import com.pokemon.pokemon_service.repository.PokedexRepository;
import com.pokemon.pokemon_service.repository.PokemonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PokemonService {
    //@Autowired
    private final RestTemplate restTemplate = new RestTemplate();

    private final PokemonRepository pokemonRepository;
    private final PokedexRepository pokedexRepository;


    public PokemonService(
            PokemonRepository pokemonRepository,
            PokedexRepository pokedexRepository
    ) {

        this.pokemonRepository = pokemonRepository;
        this.pokedexRepository = pokedexRepository;
    }

    public void cargarPokemonIniciales() {

        for (int i = 1; i <= 1025; i++) {

            try {

                if (!pokemonRepository.existsById(i)) {

                    obtenerPokemonPorId(i);

                    System.out.println(
                            "Pokemon cargado: " + i
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Error con pokemon " + i
                );

                e.printStackTrace();
            }
        }
    }



    //====================
    // Por id
    //====================

    public Pokemon obtenerPokemonPorId(Integer id) {

        try {

            // API POKEMON
            String url = "https://pokeapi.co/api/v2/pokemon/" + id;

            String json = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(json);

            Pokemon pokemon = new Pokemon();


            // DATOS BASICOS
            pokemon.setIdPokemon(root.get("id").asInt());

            pokemon.setNombre(root.get("name").asText());

            pokemon.setAltura(root.get("height").asInt());

            pokemon.setPeso(root.get("weight").asInt());

            pokemon.setExperienciaBase(root.get("base_experience").asInt());


            // IMAGEN
            pokemon.setImagenUrl(root.get("sprites").get("front_default").asText());

            // TIPOS
            pokemon.setTipo1(root.get("types").get(0).get("type").get("name").asText());

            if(root.get("types").size() > 1) {
                pokemon.setTipo2(root.get("types").get(1).get("type").get("name").asText());
            }

            // HABILIDADES
            String habilidad1 = null;
            String habilidad2 = null;
            String habilidadOculta = null;

            JsonNode abilities = root.get("abilities");

            int normales = 0;

            for (JsonNode ability : abilities) {
                String nombreHabilidad = ability.get("ability").get("name").asText();

                boolean oculta = ability.get("is_hidden").asBoolean();

                if (oculta) {

                    habilidadOculta = nombreHabilidad;

                } else {

                    if (normales == 0) {

                        habilidad1 = nombreHabilidad;

                    } else if (normales == 1) {

                        habilidad2 = nombreHabilidad;
                    }

                    normales++;
                }
            }

            pokemon.setHabilidad1(habilidad1);
            pokemon.setHabilidad2(habilidad2);
            pokemon.setHabilidadOculta(habilidadOculta);

            // STATS
            pokemon.setHp(root.get("stats").get(0).get("base_stat").asInt());

            pokemon.setAtaque(root.get("stats").get(1).get("base_stat").asInt());

            pokemon.setDefensa(root.get("stats").get(2).get("base_stat").asInt());

            pokemon.setAtaqueSp(root.get("stats").get(3).get("base_stat").asInt());

            pokemon.setDefensaSp(root.get("stats").get(4).get("base_stat").asInt());

            pokemon.setVelocidad(root.get("stats").get(5).get("base_stat").asInt());

            // API SPECIES
            String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + id;

            String speciesJson = restTemplate.getForObject(speciesUrl, String.class);

            JsonNode speciesRoot = mapper.readTree(speciesJson);


            // DESCRIPCION
            JsonNode descriptions = speciesRoot.get("flavor_text_entries");

            for (JsonNode entry : descriptions) {

                String idioma = entry.get("language").get("name").asText();

                if (idioma.equals("es")) {

                    pokemon.setDescripcion(
                            entry.get("flavor_text")
                                    .asText()
                                    .replace("\n", " ")
                                    .replace("\f", " ")
                    );

                    break;
                }
            }

            // FORMA
            pokemon.setForma(speciesRoot.get("shape").get("name").asText());

            // MAPEO GENERACION
            String generation = speciesRoot.get("generation").get("name").asText();

            Integer idPokedex = switch (generation) {

                case "generation-i" -> 1;

                case "generation-ii" -> 2;

                case "generation-iii" -> 3;

                case "generation-iv" -> 4;

                case "generation-v" -> 5;

                case "generation-vi" -> 6;

                case "generation-vii" -> 7;

                case "generation-viii" -> 8;

                case "generation-ix" -> 9;

                default -> 10;
            };


            // BUSCAR POKEDEX EN BD
            Pokedex pokedex =
                    pokedexRepository.findById(idPokedex).orElseThrow(()
                                    -> new RuntimeException("Pokedex no encontrada"));
            pokemon.setPokedex(pokedex);

            // GUARDAR
            return pokemonRepository.save(pokemon);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("Error al obtener Pokémon: " + e.getMessage());
        }
    }






    //====================
    //Por nombre
    //====================
    public Pokemon obtenerPokemon(String nombre) {

        try {
            // API POKEMON
            String url = "https://pokeapi.co/api/v2/pokemon/" + nombre;

            String json = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(json);

            Pokemon pokemon = new Pokemon();


            // DATOS BASICOS
            pokemon.setIdPokemon(root.get("id").asInt());

            pokemon.setNombre(root.get("name").asText());

            pokemon.setAltura(root.get("height").asInt());

            pokemon.setPeso(root.get("weight").asInt());

            pokemon.setExperienciaBase(root.get("base_experience").asInt());


            // IMAGEN
            pokemon.setImagenUrl(root.get("sprites").get("front_default").asText());

            // TIPOS
            pokemon.setTipo1(root.get("types").get(0).get("type").get("name").asText());

            if(root.get("types").size() > 1) {
                pokemon.setTipo2(root.get("types").get(1).get("type").get("name").asText());
            }

            // HABILIDADES
            String habilidad1 = null;
            String habilidad2 = null;
            String habilidadOculta = null;

            JsonNode abilities = root.get("abilities");

            int normales = 0;

            for (JsonNode ability : abilities) {
                String nombreHabilidad = ability.get("ability").get("name").asText();

                boolean oculta = ability.get("is_hidden").asBoolean();

                if (oculta) {

                    habilidadOculta = nombreHabilidad;

                } else {

                    if (normales == 0) {

                        habilidad1 = nombreHabilidad;

                    } else if (normales == 1) {

                        habilidad2 = nombreHabilidad;
                    }

                    normales++;
                }
            }

            pokemon.setHabilidad1(habilidad1);
            pokemon.setHabilidad2(habilidad2);
            pokemon.setHabilidadOculta(habilidadOculta);

            // STATS
            pokemon.setHp(root.get("stats").get(0).get("base_stat").asInt());
            //pokemon.setHp(999999999);

            pokemon.setAtaque(root.get("stats").get(1).get("base_stat").asInt());

            pokemon.setDefensa(root.get("stats").get(2).get("base_stat").asInt());

            pokemon.setAtaqueSp(root.get("stats").get(3).get("base_stat").asInt());

            pokemon.setDefensaSp(root.get("stats").get(4).get("base_stat").asInt());

            pokemon.setVelocidad(root.get("stats").get(5).get("base_stat").asInt());

            // API SPECIES
            String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + nombre;

            String speciesJson = restTemplate.getForObject(speciesUrl, String.class);

            JsonNode speciesRoot = mapper.readTree(speciesJson);


            // DESCRIPCION
            JsonNode descriptions = speciesRoot.get("flavor_text_entries");

            for (JsonNode entry : descriptions) {

                String idioma = entry.get("language").get("name").asText();

                if (idioma.equals("es")) {

                    pokemon.setDescripcion(
                            entry.get("flavor_text")
                                    .asText()
                                    .replace("\n", " ")
                                    .replace("\f", " ")
                    );

                    break;
                }
            }

            // FORMA
            pokemon.setForma(speciesRoot.get("shape").get("name").asText());

            // MAPEO GENERACION
            String generation = speciesRoot.get("generation").get("name").asText();

            Integer idPokedex = switch (generation) {

                case "generation-i" -> 1;

                case "generation-ii" -> 2;

                case "generation-iii" -> 3;

                case "generation-iv" -> 4;

                case "generation-v" -> 5;

                case "generation-vi" -> 6;

                case "generation-vii" -> 7;

                case "generation-viii" -> 8;

                case "generation-ix" -> 9;

                default -> 10;
            };


            // BUSCAR POKEDEX EN BD
            Pokedex pokedex =
                    pokedexRepository.findById(idPokedex).orElseThrow(()
                            -> new RuntimeException("Pokedex no encontrada"));
            pokemon.setPokedex(pokedex);

            // GUARDAR
            return pokemonRepository.save(pokemon);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("Error al obtener Pokémon: " + e.getMessage());
        }
    }
}
