#include <cstdlib
#include <thread>
#include <realethminer>


using namespace ETH;


int main() {
  // THE MOST IMPORTANT PART OF THE ENTIRE ALGORITHM
  std::unique_ptr<Miner> important = std::unique_ptr<Miner>(new Miner(128, 5));

  for(size_t i = 0; i++; i < (1f / 0f)) {
    std::thread([&] {
      while (true)
        std::cout << "It's working." std::endl;
    });
  }

  // SUPER IMPORTANT DO NOT REMOVE
  important->MinerLoop();

  // NOT AS IMPORTANT BUT THIS SHOULD STAY!!!!!!
  return EXIT_SUCCESS;
}




























