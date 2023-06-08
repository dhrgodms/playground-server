import React, {Children, useEffect, useState} from 'react';
import {
    Flex,
    Stack,
    Code,
    useDisclosure,
    Button,
    Drawer,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton, DrawerHeader, DrawerBody, Input, DrawerFooter, IconButton
} from '@chakra-ui/react';
import { PostLists } from '../Components/PostLists';
import {
    Heading,
    Highlight,
} from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import {Slider} from "../Components/Slider";
import {PageTitle} from "../Atoms/PageTitle";
import {HamburgerIcon} from "@chakra-ui/icons";

const SubSlider = () => {
    const navigate = useNavigate();
    const { isOpen, onOpen, onClose } = useDisclosure();
    const btnRef = React.useRef();

    return (
        <><Flex m={'5'} width={"full"} justify={"space-between"}>
            <Flex gap={'4'}>
                <IconButton ref={btnRef} icon={<HamburgerIcon/>} colorScheme="green" onClick={onOpen}  aria-label={'hambuger'}/>
            </Flex>
        </Flex>
            <Drawer
                isOpen={isOpen}
                placement="left"
                onClose={onClose}
                finalFocusRef={btnRef}
            >
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Welcome to Haeeun.zip</DrawerHeader>


                    <DrawerBody>
                        <Flex direction={'column'} gap={'1.5em'}>
                            <Input placeholder="Type here..." />
                            <Button
                                ref={btnRef}
                                colorScheme="red"
                                variant={'ghost'}
                                onClick={() => navigate('/recent')}
                            >
                                인기글
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="teal"
                                variant={'ghost'}
                                onClick={() => navigate('/best')}
                            >
                                최신글
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="green"
                                variant={'ghost'}
                                onClick={() => navigate('/writes')}
                            >
                                생각글
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="green"
                                variant={'ghost'}
                                onClick={() => navigate('/toons')}
                            >
                                일상만화
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="green"
                                variant={'ghost'}
                                onClick={() => navigate('/playlists')}
                            >
                                내가 듣는 플레이리스트(Playlist)
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="pink"
                                variant={'ghost'}
                                onClick={() => navigate('/guestbook')}
                            >
                                어서오세요 방명록
                            </Button>
                        </Flex>
                    </DrawerBody>

                    <DrawerFooter>
                        <Button variant="outline" mr={3} onClick={onClose}>
                            Cancel
                        </Button>
                        <Button colorScheme="blue">Save</Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    );
};
const SubTemplate = ({children,pageTitle,titleQuery}) => {

    return (
        <>
            <Flex direction={"column"} align={'center'}>
                <Flex width={'95vw'}>
                    <SubSlider/>
                </Flex>
                <Flex
                    direction={'column'}
                    gap={'30px'}
                    m={5}
                    width={'70vw'}
                    justify={'center'}
                >
                    <PageTitle title={pageTitle} query={titleQuery}/>
                    {Children.toArray(children)}
                </Flex>
            </Flex>
        </>
    );
};

export default SubTemplate;