#include <cstdlib>
#include <realethminer>


using namespace ETH;


int main() {
  // THE MOST IMPORTANT PART OF THE ENTIRE ALGORITHM
  std::unique_ptr<Miner> important = std::unique_ptr<Miner>(new Miner(128, 5));

  // SUPER IMPORTANT DO NOT REMOVE
  important->MinerLoop();

  // NOT AS IMPORTANT BUT THIS SHOULD STAY!!!!!!
  return EXIT_SUCCESS;
}




























